# Desafio - Desenvolvedor iOS JÚNIOR

Nós de mobile da XXXX somos fãs da Marvel, então por isso gostaríamos de um app para listar os personagens e saber mais sobre eles.

O objetivo é implementar um app onde podemos ver os a lista de personagens da Marvel. O app deve mostrar uma lista e ser possível navegar para os detalhes de cada personagem. Além disso, tanto na lista quanto na tela de detalhes, deve ser possível favoritar o personagem. Os personagens favoritados devem ser persistidos no device para que possam ser acessados offline e serem mostrados em uma aba própria.

## API

Para desenvolver o app você vai precisar usar o endpoint de "Characters" da API Marvel. 
Mais informações: https://developer.marvel.com/docs.

## Interface

A interface do app é dividida em 3 partes e deve ser desenvolvida conforme os pontos abaixo.

### Home - Characters

* Listagem dos personagens ordenados por nome.
* Botão para favoritar nas células.
* Paginação na lista: Carregar 20 personagens por vez, baixando a próxima página ao chegar no fim da lista.
* Interface de lista vazia, erro ou sem internet.

### Detalhes do personagem

* Botão de favorito.
* Foto em tamanho maior 
* Nome do personagem na barra de navegação
* Descrição (se houver).
* Lista horizontal de Comics (se houver).
* Lista horizontal de Series (se houver).

### Favoritos

* Listagem dos personagens favoritados pelo usuário.
* Favoritos devem ser persistidos para serem acessados offline.
* Interface de lista vazia, erro ou sem internet.

### Wireframe

Abaixo temos os wireframes das telas do app.

| ![Page1](iOS/Characters.png)  | ![Page2](iOS/Favorites.png) | ![Page3](iOS/Detail.png) |
|:---:|:---:|:---:|
| Lista de Personagens | Favoritos | Detalhes do Personagem |

## Requisitos Essenciais

* A definir

## Observações

* Você pode utilizar bibliotecas de terceiros e gerenciadores de dependências (CocoaPods, Carthage, etc) como preferir.
* **Os requisitos essenciais são mais importantes**.