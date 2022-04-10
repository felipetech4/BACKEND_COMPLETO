
//criei o banco de dados usando o comando "use" (o banco só fica visível quando um documento é criado)
RESPOSTA 1:



use tech4music



//criei a Collection com apenas um documento e consultei para saber se estava tudo certo
//criei os outros dois documentos, e finalizei consultando tudo novamente.
RESPOSTA 2:

db.musicas.insertOne({"titulo":"Forever", "artista":"Kiss", "album":"Hot in the Shade", "genero":"Rock", "anoLancamento":1989, "compositor":"Paul Stanley"})

db.musicas.find()

db.musicas.insertMany([{"titulo":"Algo parecido", "artista":"Skank", "album":"Os três primeiros", "genero":"Pop", "anoLancamento":2018, "compositor":"Samuel Rosa"},
{"titulo":"O que me importa", "artista":"Marisa Monte", "album":"Memórias, crônicas e declarações de amor", "genero":"MPB", "anoLancamento":2000, "compositor":"Jose de Ribamar Cury"}])

db.musicas.find()
