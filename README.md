# MÓDULO DE GESTÃO DE OS PARA GRUPOS DINÂMICOS 

### PASSO A PASSO PARA DEPLOY DO MÓDULO (MYSQL)

1. Rodar o script para criar os menus e as tabelas da aplicação 
-base_dados_mod_grupodinamico.sql

2. Adicionar a configuração de banco no arquivo context do tomcat 
- No tomcat de produção a configuração deve ser feita no arquivo server.xml seguindo o modelo do módulo financeiro

<!-- Configuracao do MySql --!>
  <Resource name="jdbc/sysaid" auth="Container"
    type="javax.sql.DataSource" driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/sysaid"
    username="root" password="rootdb" maxActive="20" maxIdle="10" maxWait="-1"/>
	
<!-- /Configuracao do MySql -->

3. No arquivo persistence.xml do projeto, verificar se a configuração do dialeto está setada corretamente:
  <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle10gDialect"/>
  <property name="hibernate.default_schema" value="SYSAIDHOM"/>

4. Colocar a pasta "gestaoos com os arquivos html do projeto dentro da pasta do sysad ($SYSAID_HOME/)


### TODO:

#### Tipo de indisponibilidade
  - Título da tela - OK
  - Alterar texto da descrição - OK
  - Margens e improves de arte final no layout - OK
  - Nome da coluna actions -> Ações - OK
  - Alterar links de botões de ação - OK
  - Aumentar with do dialog de confirmação (arquivo template) - OK

#### Lista de OSs
  - [A] Colocar filtro por status (filtro persistente)
  - [A] Verificar dados que não estão sendo apresentados na lista de OS
  - [A] Mostrar descrição da OS
  - [A] Apresentar informação da SUB OS
  - [A] Ordenar lista de OS por data de criação DESC (mais nova vai para o Topo) 
  - [A] Colocar verificacao na query select para recuperar somente OS nao arquivadas
  - Ajustar tamanhos de colunas
  - [A] Ao selecionar 2 OS, estando uma bloqueada por seleção de outro usuário, bloquear o botão de carregar
  - [A] Ao vincular uma OS a um técnico, disponibilizar a seleção de OSs na lista (liberar os depois que salvar vinculo)
  - [A] Refazer a logica de bloqueio de seleção de OS por mais de um usuário 
  - [A] Verificar dados importante para equipe da Central de Serviços
  - [A] Verificar quais dados não estão sendo apresentados na tabela de lista de OS
  - [A] Melhor visualização das informações na tabela de OSs em aberto no popup de vinculo
  

  ######## Modal Seleção de tecnico disponível
  - alterar label -> OS em aberto para "OS ativa";
  OK - Apresentar o total de OSs vinculadas ao técnico, mesmo para grupos não dinâmicos (plus)
  - apresentar modal mais a baixo (margin-top)
  - hover sobre o número -> hover sobre célula

#### Programar indisponibilidade
OK - Só apresentar a tabela após a seleção do grupo

#### Modal de Programar indisponibilidade
- Diminuir a margem inferior da modal
OK - Testes com envio de email
- Recuperar o E-mail do responsável pelo Grupo para utilizar no envio do e-mail Comunicando a Indisponibilidade

#### Relatório
- Apresentar um label da data atual (Default)
- Melhorar estilo da pesquisa por período
- Aumentar o número de BOX dos Grupos para 10
- Pintar de Azul a linha do usuário indisponível no Período
- Verificar total de OS no período


#### Geral
- Melhor marca na página atual da paginação

#### Grupo dinamico
OK- Adicionar botao par abrir popup para seleção de responsável pelo grupo 
OK- Criar popup com lista de usuários do grupo com opção de seleção de somente um
OK- Permitir alterar a seleção do Responsável pelo Grupo
OK- Após a seleção do responsável, mostrar na tabela dos Grupos - criar coluna com o nome do responsável pelo grupo

#### Grupo de indisponibilidade ???
  - Adicionar coluna de responsável pela equipe
  - Ajustar tamanho da tabela
  - Ao selecionar a linha na tabela, apresentar o item selecionado em verde (background-color: #E6F3E5)
  - Editar e excluir - links com botões e ícones



