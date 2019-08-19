# Arquitetura VIPER

- View: classe que extende de `UIView`
- Repository: classe que contém e manipula o dominio da aplicação
- Presenter: class que extende de `UIViewController` e cria o meio entre a View e outros componentes (Router e Interactor)
- Entity: modelo utilizado para acessar API e faz parte da persistência.
- Router: abstração responsável por prover quais são as rotas.

------

- Coordinator: responsável por implementar a abstração de Router, e configurar os componentes para comunicarem entre si.

# Módulos

O aplicativo está dividido em modulos:

- Challenge: Aplicação
- SimpleListCharacters: Widget
- Marvel: contém os modelos da API da Marvel e lógica para geração do `hash`
- ChallengeCore: contém os adapters utilizados pelo Repository, e uma abstração do Presenter

# Como rodar o projeto

- Necessário [Xcodegen](https://github.com/yonaskolb/XcodeGen)

```shell
brew install xcodegen
```

- Gerar xcodeproj

```shell
xcodegen generate
```

- Instalar dependências

```shell
carthage update --platform iOS
```

- Usar fastlane para testes (UI e Unit Tests):

```shell
fastlane tests
```

# Funcionalidades feitas

- Usar Swift 4.
- Interface no Storyboard usando Auto Layout.
- App universal, desenvolva uma interface que se adapte a telas maiores.
- Tratamento para falha de conexão.
- O teste não pode apresenter crash.
- Testes unitários e interface.
- Widget com os 3 primeiros personagens com ação de abrir o app no detalhe do personagem.
- Integração com fastlane para cobertura de testes.
- Acessibilidade.
