package com.api.venda.vendams.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.venda.vendams.clientHttp.ProdutoFeingClient;
import com.api.venda.vendams.model.Venda;
import com.api.venda.vendams.repository.VendaProdutoRepository;
import com.api.venda.vendams.shared.Produto;
import com.api.venda.vendams.shared.VendaDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaProdutoServiceImpl implements VendaProdutoService {

    private final ModelMapper MAPPER = new ModelMapper();

    @Autowired
    private VendaProdutoRepository repository;
    @Autowired
    private ProdutoFeingClient cadastro;

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Optional<List<VendaDto>> listAll() {

        if (repository.count() < 1) {
            return Optional.empty();
        }

        List<VendaDto> listVendaDto = repository.findAll().stream()
                .map(dat -> MAPPER.map(dat, VendaDto.class)).collect(Collectors.toList());

        return Optional.of(listVendaDto);
    }

    @Override
    public Optional<List<VendaDto>> listAllWithCodigo() {

        if (repository.count() < 1) {
            return Optional.empty();
        }

        List<VendaDto> listVendaDto = repository.findAll().stream()
                .map(dat -> MAPPER.map(dat, VendaDto.class)).collect(Collectors.toList());

        return Optional.of(listVendaDto);
    }

    @Override
    public Optional<List<VendaDto>> listAllByCodigo(String codigo) {
        Optional<List<Venda>> repositoryListResponse = repository.findAllByCodigo(codigo);

        if (repositoryListResponse.isEmpty()) {
            return Optional.empty();
        }

        List<VendaDto> listVendaDto = repositoryListResponse.get().stream()
                .map(dat -> MAPPER.map(dat, VendaDto.class)).collect(Collectors.toList());

        return Optional.of(listVendaDto);

    }

    @Override
    public Optional<List<VendaDto>> listAllByDateInterval(String data1, String data2) throws ParseException {
        if (repository.count() < 1) {
            return Optional.empty();
        }

        List<Venda> repositoryListResponse = repository.findAll();

        Date dateData1 = sdf.parse(data1);
        Date dateData2 = sdf.parse(data2);

        List<VendaDto> datesInInterval = repositoryListResponse.stream().filter(e -> {

            Date VendaDate = new Date();
            try {
                VendaDate = sdf.parse(e.getDataVenda());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }

            // checando para ver se esta dentro do periodo
            if ((VendaDate.compareTo(dateData1) >= 0) && (VendaDate.compareTo(dateData2) <= 0)) {
                return true;
            } else if ((VendaDate.compareTo(dateData2) >= 0) && (VendaDate.compareTo(dateData1) <= 0)) {
                return true;
            } else {
                return false;
            }
        }).map(e -> MAPPER.map(e, VendaDto.class)).collect(Collectors.toList());

        if (datesInInterval.size() < 1) {
            return Optional.empty();
        }

        return Optional.of(datesInInterval);

    }

    @Override
    public Optional<VendaDto> listUnique(String id) {

        Optional<Venda> repositoryResponse = repository.findById(id);

        if (repositoryResponse.isEmpty()) {
            return Optional.empty();
        }

        VendaDto vendaDtoResponse = MAPPER.map(repositoryResponse.get(), VendaDto.class);

        return Optional.of(vendaDtoResponse);
    }

    @Override
    public Optional<VendaDto> postUnique(VendaDto venda) {
        
        Optional<Boolean> operationSucces = cadastro.putStock(venda.getCodigo(),
        false /* false para retirar do estoque */, venda.getQuantidadeVendida());
        
        // se não foi possivel retirar do estoque a venda é cancelada
        if (operationSucces.isEmpty() || (!operationSucces.get())) {
            return Optional.empty();
        }
        
        Venda vendaRequest = MAPPER.map(venda, Venda.class);
        Produto produtoRequest = cadastro.getProduto(vendaRequest.getCodigo()).get();
        
        // guardando o produto dentro da venda
        vendaRequest.setProduto(produtoRequest);
        vendaRequest.setTotalPreco();

        // caso não tenha data pre-definida é cadastrado a data atual
        if (vendaRequest.getDataVenda() == null || vendaRequest.getDataVenda().isBlank()
                || vendaRequest.getDataVenda().isEmpty()) {
            String actualDate = sdf.format(Calendar.getInstance().getTime());

            vendaRequest.setDataVenda(actualDate);
        }

        Venda repositoryResponse = repository.save(vendaRequest);

        
        return Optional.of(MAPPER.map(repositoryResponse, VendaDto.class));
    }

    @Override
    public Optional<String> cancelById(String id) {
        Optional<Venda> venda = repository.findById(id);
        if (venda.isPresent()) {
            cadastro.putStock(venda.get().getCodigo(), true, venda.get().getQuantidadeVendida());

            repository.deleteById(id);
            return Optional
                    .of(String.format("REGISTRO DA VENDA: '%s' CANCELADO COM SUCESSO! O PRODUTO VOLTOU PARA O ESTOQUE.",
                            venda.get().getId()));
        }
        return Optional.of("REGISTRO INFORMADO NÃO EXISTE");
    }

    @Override
    public Optional<String> deleteById(String id) {
        Optional<Venda> venda = repository.findById(id);
        if (venda.isPresent()) {
            cadastro.putStock(venda.get().getCodigo(), true, venda.get().getQuantidadeVendida());

            repository.deleteById(id);
            return Optional.of(String.format("REGISTRO DA VENDA: '%s' DELETADO COM SUCESSO!",
                    venda.get().getId()));
        }
        return Optional.of("REGISTRO INFORMADO NÃO EXISTE");
    }
}
