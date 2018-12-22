#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import print_function

import grpc

import helloworld_pb2
import helloworld_pb2_grpc


def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = helloworld_pb2_grpc.GreeterStub(channel)
  
  #adicionar contato
  response = stub.AdicionarContato(helloworld_pb2.Contato(nome='Renan',telefone='666'))
  print("Adicionado: " + response.message)

  #adicionar contato
  response = stub.AdicionarContato(helloworld_pb2.Contato(nome='Mateus',telefone='123'))
  print("Adicionado: " + response.message)

  #ver contatos
  response = stub.ListarContatos(helloworld_pb2.Request(message='None'))
  print("Lista: \n" + response.message)

  #remover contato
  response = stub.RemoverContato(helloworld_pb2.Nome(nome='Mateus'))
  print("Remover: " + response.message)

  #ver contatos
  response = stub.ListarContatos(helloworld_pb2.Request(message='None'))
  print("Lista: \n" + response.message)


if __name__ == '__main__':
  run()