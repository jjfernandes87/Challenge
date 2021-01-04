# Desafio - Desenvolvedor Android

Nós de mobile somos fãs da Marvel :heart_eyes:, então por isso gostaríamos de um app para listar os personagens e saber mais sobre eles :rocket:.

O objetivo é implementar um app :iphone: onde podemos ver a lista de personagens da Marvel. O app deve mostrar uma lista e ser possível navegar para os detalhes de cada personagem :space_invader:. Além disso, tanto na lista quanto na tela de detalhes, deve ser possível favoritar :star: o personagem. Os personagens favoritados devem ser persistidos no device para que possam ser acessados offline e serem mostrados em uma aba própria.

## API

Para desenvolver o app :iphone: você vai precisar usar o endpoint de `"Characters"` da API Marvel. 
Mais informações: https://developer.marvel.com/docs.

## Interface

- [x] A interface do app :iphone: é dividida em **3 partes** e deve ser desenvolvida conforme os pontos abaixo.

### Home - Characters

- [x] Listagem dos personagens ordenados por ordem :abc: alfabética.
- [x] Botão para favoritar :star: personagem nas células.
- [x] Pull-to-refresh :arrows_counterclockwise: para atualizar a lista.
- [x] Altenar modo de exibição entre **grid** ou **list**.
- [x] Paginação na lista:
    - Carregar **20 personagens** :space_invader: por vez, baixando a próxima página ao chegar no fim da lista.
- [x] Interface de :warning: lista vazia, erro ou sem internet.

### Detalhes do personagem

- [x] Botão de favorito :star:.
- [x] Foto :foggy: em tamanho maior.
- [x] Nome do personagem na barra de navegação.
- [x] Descrição do personagem :space_invader: se houver, caso contrário exibir uma mensagem  *"sem descrição."* .
- [x] Lista horizontal de Comics *(se houver)*.
- [x] Lista horizontal de Series *(se houver)*.
- [x] Interface de lista vazia, :no_mobile_phones: erro ou sem internet.

### Favoritos

- [x] Listagem dos personagens favoritados pelo usuário *(exibindo apenas o nome e imagem do personagem)*.
- [x] Não há limite de personagens a serem favoritados :metal:.
- [x] Favoritos devem ser persistidos (apenas nome e imagem do personagem) localmente para serem acessados offline.

### Wireframe

Abaixo :eyes: temos os wireframes das telas do app.

| ![Page1](android/Characters.png)  | ![Page2](android/Favorites.png) | ![Page3](android/Detail.png) |
|:---:|:---:|:---:|
| Lista de Personagens | Favoritos | Detalhes do Personagem |

## Requisitos Essenciais

- [x] Usar Kotlin.
- [x] Injeção :syringe: de dependência com Dagger ou similar.
- [x] Desenvolver o App em uma arquitetura robusta.
- [x] Tratamento para :no_mobile_phones: falha de conexão.
- [x] O app não pode apresentar crash :boom:.
- [x] Testes :clipboard: unitários.

## Bônus

- [ ] Desenvolva uma interface que se adapte a telas maiores *(ex.: Tablet)*.
- [ ] **Barra de busca** para filtrar lista de pesonagens por nome.
- [ ] Testes de interface :+1:.

## Observações

- [x] Fique a vontade :point_up: para usar quantas bibliotecas quiser ou designer partner que você achar pertinente em sua solução. Estamos interessados em saber se você está atualizado com as melhores práticas em desenvolvimento Android. 
- [x] Um código bem organizado e que se preocupe com o alto desempenho :chart_with_downwards_trend: será um diferencial.
- [x] Fique a vontade :wave: para trabalhar com os dados usando UserDefault, SQLite, Realm ou cache de serviço.
