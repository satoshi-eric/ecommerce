# Curso de Kafka da Alura

## Introdução

No modelo de sistemas, componentes se comunicavam entre si por meio de HTTP e todos se conhecem.

Com o Kafka e sistemas de mensageria, existe um componente chamado broker que recebe todas as mensagens e tópicos enquanto outros componentes ouvem esses tópicos e processam a partir deles.

## Instalação

Antes de instalar o Kafka, é necessário instalar o Java.
Para instalar o Kafka, seguir os seguintes passos:

* Baixar o binário do Kafka em https://kafka.apache.org/downloads.
* Mover pasta para um local próximo da raiz. **Obs**: ao rodar os comandos em um local muito longe da raíz (c:\pasta\pasta\pasta), o kafka reclama que o nome do caminho é muito longo.
* Descompactar o binário na pasta onde o arquivo compactado foi movido

Para rodar o kafka, seguir seguintes passos:

**Obs**: para rodar o kafka, vários scripts em shell são utilizados. O windows não sabe executar esses scripts, para isso, existem arquivos bat na pasta windows. É necessários utilizá-los em vez dos scripts padrão.

* Antes de executar o Kafka, é necessário rodar o Zookeeper antes. Isso é necessário pois o kafka é o processsador que envia e recebe mensagens, mas ele precisa de um lugar para armazenar informações, no caso, o Zookeeper.
```shell
.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

* Inicializar o Kafka (Por padrão é inicilizado na porta 9092):
```shell
.\bin\windows\kafka-server-start.bat config\server.properties
```

* Criar um tópico:
```shell
.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVO_PEDIDO
  ```

* Listar tópicos:
```shell
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092  
  ```

* Rodar um produtor:
```shell
.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic LOJA_NOVO_PEDIDO
```

* Rodar um consumidor:
```shell
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic LOJA_NOVO_PEDIDO
```
Inserir `--from-beginning` para exibir mensagens desde o começo

* Descrever os tópicos:
```shell
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --describe
```

* Alterar tópico:
```shell
.\bin\windows\kafka-topics.bat --alter --bootstrap-server localhost:9092 --topic ECOMMERCE_NEW_ORDER --partitions 3 
```

* Descrever grupos de consumo:
```shell
.\bin\windows\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --describe --all-groups
```
