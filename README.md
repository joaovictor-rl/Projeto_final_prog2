# Projeto Final — Programação de Computadores II (2026.2)

## Discentes

— João Victor Reis  
— João Emannuel  
— Alice Barros  

Sistema de **caixa/mercado (PDV)** em Java, desenvolvido em console, aplicando os conceitos de Programação Orientada a Objetos trabalhados na disciplina: herança, interfaces, encapsulamento e composição entre classes.

O sistema simula o dia a dia de um pequeno mercado: gerentes cadastram produtos e funcionários, e operadores de caixa realizam vendas, aplicam descontos, processam pagamentos e emitem recibos, com baixa automática de estoque.

## Funcionalidades

### Gerente
— Cadastrar novos operadores de caixa  
— Cadastrar produtos  
— Adicionar ou baixar estoque de um produto  
— Listar produtos cadastrados  
— Verificar estoque total  
— Emitir relatório de vendas  
— Alterar a própria senha  

### Operador de Caixa
— Iniciar uma nova venda  
— Registrar itens no carrinho (com validação de estoque disponível)  
— Remover itens do carrinho  
— Aplicar desconto na venda  
— Registrar pagamento (Cartão de Crédito/Débito, Pix ou Dinheiro, com cálculo de troco)  
— Emitir recibo  
— Finalizar a venda, com baixa automática do estoque dos itens vendidos  

## Estrutura do projeto

```
Projeto_final_prog2/
├── código/
│   ├── Main.java            — Ponto de entrada e menus do sistema (login, gerente, operador)
│   ├── Usuario.java         — Classe abstrata base para os usuários do sistema
│   ├── Gerente.java         — Usuário com permissões administrativas
│   ├── OperadorCaixa.java   — Usuário responsável pelas vendas
│   ├── Empresa.java         — Gerencia o catálogo de produtos e o estoque
│   ├── Produto.java         — Representa um produto do mercado
│   ├── Carrinho.java        — Carrinho de compras de uma venda
│   ├── ItemCarrinho.java    — Item (produto + quantidade) dentro do carrinho
│   ├── Venda.java           — Registro de uma venda (total, desconto, status, recibo)
│   ├── Pagamento.java       — Dados de um pagamento (forma, valor, status)
│   ├── FormaPagamento.java  — Interface implementada pelas formas de pagamento
│   ├── Cartao.java          — Pagamento via cartão de crédito/débito
│   ├── Pix.java             — Pagamento via Pix
│   └── Dinheiro.java        — Pagamento em dinheiro, com cálculo de troco
├── diagramas/
│   └── Diagrama_Sequencia_Venda.png  — Diagrama de sequência do fluxo de venda
└── README.md
```

## Modelagem (POO)

— **Herança:** `Gerente` e `OperadorCaixa` estendem a classe abstrata `Usuario`, compartilhando dados de login (nome, CPF, senha) e especializando o comportamento de cada perfil.  
— **Interface e polimorfismo:** `FormaPagamento` define o contrato `realizarPagamento(Pagamento)`, implementado de forma distinta por `Cartao`, `Pix` e `Dinheiro`.  
— **Composição:** `Venda` é composta por um `Carrinho`, que por sua vez agrega vários `ItemCarrinho`; `Empresa` mantém a lista de `Produto` disponíveis para venda.  

O diagrama de sequência do fluxo de venda está disponível em [`diagramas/Diagrama_Sequencia_Venda.png`](diagramas/Diagrama_Sequencia_Venda.png).

## Usuários de demonstração

O sistema já inicia com dois usuários e cinco produtos cadastrados, para facilitar os testes:

| Perfil    | CPF           | Senha      |
|-----------|---------------|------------|
| Gerente   | 00000000000   | admin      |
| Operador  | 22222222222   | senha123   |

## Fluxo básico de uso

1. Faça login como **Gerente** ou **Operador de Caixa**.
2. Como gerente: cadastre produtos/funcionários ou acompanhe o estoque e as vendas.
3. Como operador: inicie uma venda, adicione produtos ao carrinho, aplique desconto (se houver), registre o pagamento e finalize a venda — o estoque é atualizado automaticamente.
