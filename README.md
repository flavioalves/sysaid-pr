# MÓDULO DE GESTÃO DE OS PARA GRUPOS DINÂMICOS 

### PASSO A PASSO PARA DEPLOY DO MÓDULO (MYSQL)

1. Rodar o script para criar os menus e as tabelas da aplicação 

2. Adicionar a configuração de banco no arquivo context do tomcat 

  <Resource name="jdbc/sysaid" auth="Container"
    type="javax.sql.DataSource" driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/sysaid"
    username="root" password="rootdb" maxActive="20" maxIdle="10" maxWait="-1"/>

3. No arquivo persistence.xml do projeto, verificar se a configuração do dialeto está setada corretamente:
  <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle10gDialect"/>
  <property name="hibernate.default_schema" value="SYSAIDHOM"/>

4. Colocar a pasta "gestaoos com os arquivos html do projeto dentro da pasta do sysad ($SYSAID_HOME/)


### TODO:

#### Tipo de indisponibilidade
  - Título da tela - OK
  - Alterar texto da descrição- OK
  - Margens e improves de arte final no layout - OK
  - Nome da coluna actions -> Ações - OK
  - Alterar links de botões de ação - OK
  - Aumentar with do dialog de confirmação (arquivo template)

#### Lista de OSs
  - [A] Colocar filtro por status (filtro persistente)
  - Ajustar tamanhos de colunas
  - [A] Ao selecionar 2 OS, estando uma bloqueada por seleção de outro usuário, bloquear o botão de carregar
  - [A] Ao vincular uma OS a um técnico, disponibilizar a seleção de OSs na lista (liberar os depois que salvar vinculo)

  ######## Modal Seleção de tecnico disponível
  - alterar label -> OS em aberto para "OS ativa";
  OK - Apresentar o total de OSs vinculadas ao técnico, mesmo para grupos não dinâmicos (plus)
  - apresentar modal mais a baixo (margin-top)
  - hover sobre o número -> hover sobre célula

#### Programar indisponibilidade
- Só apresentar a tabela após a seleção do grupo

#### Modal de Programar indisponibilidade
- Diminuir a margem inferior da modal
- Testes com envio de email

#### Relatório
- Apresentar um label da data atual (Default)
- Melhorar estilo da pesquisa por período

#### Geral
- Melhor marca na página atual da paginação

#### Grupo de indisponibilidade
  - Adicionar coluna de responsável pela equipe
  - Ajustar tamanho da tabela
  - Ao selecionar a linha na tabela, apresentar o item selecionado em verde (background-color: #E6F3E5)
  - Editar e excluir - links com botões e ícones

#### Feedbacks do cliente
  - Atendentes Nívea, Fátima, Núbia
  - Filtro por status (aberta, pendente-sub-os,...)
  - Existem alguns dados que não estão sendo apresentados na lista de OS
  - Possibilidade de vincular mais de uma OS para um técnico (testar)
  - Na lista de OSs, ordenar pela data/hora de criação (mais novas no topo da tela)
  - Opção para edição de OS - Link para edição
  - O total de OS vinculadas não está sendo apresentadas
  - No relatório, o total de OS do período nào está sendo contabilizado
  - Na informação de OSs em aberto, na tela de vínculo - aumentar width para melhor visualização do detalhe
  - Tratar OSs arquivadas e não arquivadas
  - TESTES DE CADASTRO DE OS NA FORMA NATURAL
  - Tela de Vínculo da OS
    - É importante ver a descrição da OS.
    - Categoria, sub-categoria e assunto
    - Apresentar Informação do status das SUB-OS  

