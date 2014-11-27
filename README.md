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
  - Título da tela
  - Alterar texto da descrição
  - Margens e improves de arte final no layout
  - Nome da coluna actions -> Ações
  - Alterar links de botões de ação
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



