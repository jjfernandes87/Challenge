# Desafio Android

Nesse desafio foi feito o consumo da API da Marvel (https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0) para ser apresentadas as seguintes telas:

- Tela 1: Através de uma TabLayout + ViewPager2, foi listado os 20 primeiros personagens da mavel, em ordem alfabética (Padrão da API) na primeira tabview, e na segunda foi apresentada a lista de personagens favoritos. 

- Tela 2: Detalhes de um pesonagem, renderizando imagem e descrição do mesmo

[obs]: existe responsividade para todos os eventos em que é alterado o status de um personagem favorito.

# Arquitetura e Tecnologias Utilizadas:

O projeto é multimodulo e foi feito com a arquiteura MVVM.

![image](https://user-images.githubusercontent.com/9469620/159884526-6f651a69-88f5-4e9c-a0d0-e6761e23389d.png)


As dependenica foram feitas com Versions Catalogs (https://docs.gradle.org/current/userguide/platforms.html#sec:sharing-catalogs)
Conexão com API: Retrofit + HttpCLient + OkHttp
Deserialização de objetos: Json + Kotlin serialization
Para autenticar as chamadas foi feito uso de um Interceptor.
Injeção de dependencia: Kodein
Método para salvar dados em database local: Room
Renderização de imagens: Glide


![image](https://user-images.githubusercontent.com/9469620/159884603-95aff5fb-e4f2-4975-9a28-65a122f56a19.png)
![image](https://user-images.githubusercontent.com/9469620/159884632-51f40cdf-bdfd-4741-9b8f-0eaa69e61efa.png)
![image](https://user-images.githubusercontent.com/9469620/159884661-d349aaa7-fe30-4e91-95e1-ecdb25e5a314.png)ø


