#!/usr/bin/env python
# -*- coding: utf-8 -*-

from concurrent import futures
import time

import grpc

import helloworld_pb2
import helloworld_pb2_grpc

_ONE_DAY_IN_SECONDS = 60 * 60 * 24


class Greeter(helloworld_pb2_grpc.GreeterServicer):
  contatos = []

  def AdicionarContato(self, request, context):
    x = Contato(str(request.nome),str(request.telefone))
    self.contatos.append(x)
    result = "Contato Adicionado \n\t Nome: " + request.nome + "|Telefone: " + request.telefone + " \n"


    print result 
    return helloworld_pb2.Resposta(message=result)


  def RemoverContato(self, request, context):
    result = "Contato Inexistente!"

    for i in range(len(self.contatos)):
      if (self.contatos[i].nome == request.nome):
        self.contatos.pop(i)
        result = "Contato removido "+request.nome

        return helloworld_pb2.Resposta(message=result) 

    print result
    return helloworld_pb2.Resposta(message=result)

  def ListarContatos(self, request, context):
    result = ""
    
    for i in range(len(self.contatos)):
      result += "Nome: "+self.contatos[i].nome+"|Tel: "+self.contatos[i].telefone+"\n"

    print(result)
    return helloworld_pb2.Resposta(message=result)


class Contato():
	def __init__(self, nome, telefone):
		self.nome = nome
		self.telefone = telefone

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

