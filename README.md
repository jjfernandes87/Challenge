# Marvel App

Aplicativo Android que consome a API de personagens de quadrinhos da Marvel, documentada em https://developer.marvel.com/docs. 

Os testes utilizados são exclusivamente unitários onde foram testadas as View Models em conjunto com os Interactors utilizando a biblioteca de testes Mockito.

A comunicação entre View e View Model é feita utilizando o conceito de State, onde os States são unicos para cada view e representam seu estado, estes por sua vez são expostos através de um Observable público na View Model.

# Arquitetura
 * Clean Architecture
 * MVVM

# Tecnologias

* RxJava 2
* RxKotlin
* RxBinding
* Paging Library 2
* Hilt & Dagger 2
* Room
* Retrofit
* Mockito
