#!/usr/bin/env python
# -*- coding: utf-8 -*-

from concurrent import futures
import time
import grpc
import helloworld_pb2
import helloworld_pb2_grpc

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class Greeter(helloworld_pb2_grpc.GreeterServicer):
  livros = []

  def AdicionarLivro(self, request, context):
    x = Livro(str(request.titulo),str(request.autor),str(request.qtdPag),str(request.genero),str(request.ano),)
    self.livros.append(x)
    result = "Livro Adicionado \n\t Titulo : " + request.titulo + "| Autor: " + request.autor + "| Paginas : " + request.qtdPag + "| Genero: " + request.genero +"|Ano : " + request.ano + "\n"


    print result 
    return helloworld_pb2.Resposta(message=result)


  def RemoverLivro(self, request, context):
    result = "Livro Inexistente!"

    for i in range(len(self.livros)):
      if (self.livros[i].titulo == request.titulo):
        self.livros.pop(i)
        result = "Livro removido " + request.titulo

        return helloworld_pb2.Resposta(message=result) 

    print result
    return helloworld_pb2.Resposta(message=result)

  def ListarLivros(self, request, context):
    result = ""
    
    for i in range(len(self.livros)):
      result += "Titulo : " + self.livros[i].titulo + "| Autor: " + self.livros[i].autor + "| Paginas : " + self.livros[i].qtdPag + "| Genero: " + self.livros[i].genero + "| Ano: " + self.livros[i].ano + " \n"

    print(result)
    return helloworld_pb2.Resposta(message=result)


class Livro():
	def __init__(self, titulo, autor, qtdPag, genero, ano):
		self.titulo = titulo
		self.autor = autor
		self.qtdPag = qtdPag
		self.genero = genero
		self.ano = ano

def serve():
	server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
	helloworld_pb2_grpc.add_GreeterServicer_to_server(Greeter(), server)
	server.add_insecure_port('[::]:50051')
	server.start()

	try:
		while True:
			time.sleep(_ONE_DAY_IN_SECONDS)
	except KeyboardInterrupt:
		server.stop(0)

		

if __name__ == '__main__':
	serve()

