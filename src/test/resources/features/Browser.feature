# language: pt
@browser
Funcionalidade: Acessar site

  @browser1
  Esquema do Cenario: Acessar site <site>
    Dado que eu acesse o site "<site>"
    Quando serão apresentadas informações do dia
    Então estarei atualizado com o que está acontecendo no momento

    Exemplos: 
      | site                  |
      | http://www.uol.com.br |
