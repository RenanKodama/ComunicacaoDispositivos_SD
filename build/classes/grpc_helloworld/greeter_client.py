#!/usr/bin/env python
# -*- coding: utf-8 -*-

from __future__ import print_function

import grpc

import helloworld_pb2
import helloworld_pb2_grpc

def run():
  channel = grpc.insecure_channel('localhost:50051')
  stub = helloworld_pb2_grpc.GreeterStub(channel)
  
  #adicionar Livro
  response = stub.AdicionarLivro(helloworld_pb2.Livro(titulo='Primo Basilio',autor='Eca de Queiroz',qtdPag='520',genero='Romance',ano='1995'))
  print("Adicionado: " + response.message)

  #adicionar Livro
  response = stub.AdicionarLivro(helloworld_pb2.Livro(titulo='A Arte da Guerra',autor='Sun Tzu',qtdPag='220',genero='Estrategia',ano='1980'))
  print("Adicionado: " + response.message)

  #adicionar Livro
  response = stub.AdicionarLivro(helloworld_pb2.Livro(titulo='O Espadachim de Carvao',autor='Affonso Solano',qtdPag='347',genero='Ficcao', ano='1980'))
  print("Adicionado: " + response.message)

  #Listar Livros
  response = stub.ListarLivros(helloworld_pb2.Request(message='None'))
  print("Lista: \n" + response.message)

  #Remover Livro
  response = stub.RemoverLivro(helloworld_pb2.Titulo(titulo='A Arte da Guerra'))
  print("Remover: " + response.message)

  #Visualizar Livros após remoção
  response = stub.ListarLivros(helloworld_pb2.Request(message='None'))
  print("Lista: \n" + response.message)


if __name__ == '__main__':
  run()
