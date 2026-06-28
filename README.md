# Clínica VidaPlena 🏥

Sistema de gerenciamento de clínica feito em Java puro (sem framework, sem banco de dados, só JDK e força de vontade), pra colocar em prática os conceitos de Programação Orientada a Objetos: herança, interfaces, polimorfismo, exceções personalizadas e umas coleções (`HashMap`, `HashSet`, `List`) pra organizar tudo.

A ideia geral é simular o dia a dia de uma clínica fictícia chamada **VidaPlena**, que atende quatro especialidades: clínica geral, fisioterapia, psicologia e nutrição. Dá pra cadastrar pacientes, profissionais, marcar consulta, registrar atendimento, calcular pagamento e tirar relatório no final. Tudo rodando no terminal, com um menu numerado mesmo (aquele clássico `1 - Cadastrar`, `2 - Listar`...).

## O que o sistema faz

- **Pacientes**: cadastro, busca por CPF (rapidinho, com HashMap), ativar/desativar, complementar dados depois e listar quem está ativo ou não.
- **Profissionais**: cada especialidade tem sua própria classe (`ClinicoGeral`, `Fisioterapeuta`, `Psicologo`, `Nutricionista`), cada uma com um campinho extra que faz sentido pra ela — tipo o fisioterapeuta tem "sessões previstas", o psicólogo tem "abordagem terapêutica", etc.
- **Consultas**: agendar, cancelar (com motivo), remarcar. O sistema verifica se o profissional atende naquele dia da semana e se não tem outro paciente marcado no mesmo horário.
- **Atendimentos**: depois que a consulta acontece, dá pra registrar um atendimento, que gera um prontuário com observações, diagnóstico e procedimentos.
- **Pagamentos**: três formas — dinheiro/pix (5% de desconto), cartão (parcela em até 6x, com taxinha se passar de 3x) e convênio (desconta o percentual de cobertura, e cada convênio só cobre certas especialidades).
- **Convênios**: três pré-cadastrados (SaudePlus, VidaMais e BemEstar), cada um cobrindo especialidades diferentes.
- **Relatórios**: lista geral de pessoas (usando polimorfismo pra mostrar dados certos pra cada tipo), resumo financeiro (quanto faturou, quantas consultas foram realizadas/canceladas, multas) e exportação dos dados das consultas.

Tem até uma regrinha de multa: se o paciente cancelar a consulta com menos de 2 horas de antecedência, leva uma multa de R$50.

## Como o código tá organizado

A classe `Clinica` é meio que o cérebro de tudo — ela guarda as listas e os mapas de pacientes, profissionais, consultas, atendimentos e pagamentos, e é nela que ficam as regras de negócio (verificar conflito de horário, validar cancelamento, etc).

A hierarquia de classes ficou assim:

```
Pessoa (abstrata)
 ├── Paciente
 └── Profissional (abstrata)
      ├── ClinicoGeral
      ├── Fisioterapeuta
      ├── Psicologo
      └── Nutricionista

Pagamento (abstrata)
 ├── PagamentoDinheiro
 ├── PagamentoCartao
 └── PagamentoConvenio
```

E duas interfaces dão o contrato pra quem precisa:

- `Agendavel` → quem pode ser agendado, cancelado e remarcado (a `Consulta` implementa)
- `Exportavel` → quem sabe se exportar como texto (`Consulta`, `Atendimento` e `Pagamento` implementam)

Tem também um punhado de exceções customizadas pra cobrir os perrengues do dia a dia: paciente inativo, paciente/profissional não encontrado, horário indisponível, consulta não encontrada, operação inválida, pagamento inválido e convênio que não cobre a especialidade. Cada uma força o programa a lidar com a situação em vez de simplesmente quebrar.

## Como rodar

Se preferir, dá pra abrir o projeto direto numa IDE  é só apertar o play na `Main`.

