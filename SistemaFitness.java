import java.util.*;
import java.time.LocalDate;

// ============================================
// CLASSE PRINCIPAL

// ============================================
public class SistemaFitness {
    static Scanner scanner = new Scanner(System.in);
    static List<Cliente> clientes = new ArrayList<>();
    static List<Avaliacao> avaliacoes = new ArrayList<>();
    static List<Plano> planos = new ArrayList<>();
    static List<Consulta> consultas = new ArrayList<>();
    static List<Pagamento> pagamentos = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   SISTEMA FITNESS E NUTRICAO");
        System.out.println("===========================================\n");
        
        menuPrincipal();
        
        System.out.println("\nSistema encerrado!");
    }
    
    // MENU PRINCIPAL
    static void menuPrincipal() {
        while (true) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Clientes");
            System.out.println("2. Avaliacoes Fisicas");
            System.out.println("3. Planos");
            System.out.println("4. Consultas");
            System.out.println("5. Financeiro");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuAvaliacoes();
                case 3 -> menuPlanos();
                case 4 -> menuConsultas();
                case 5 -> menuFinanceiro();
                case 0 -> { return; }
                default -> System.out.println("Opcao invalida!");
            }
        }
    }
    
    // ========== MENU CLIENTES ==========
    static void menuClientes() {
        while (true) {
            System.out.println("\n===== CLIENTES =====");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Excluir");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> excluirCliente();
                case 0 -> { return; }
            }
        }
    }
    
    static void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Tipo (1-Normal / 2-VIP): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        Cliente cliente = new Cliente();
        cliente.id = System.currentTimeMillis();
        cliente.nome = nome;
        cliente.cpf = cpf;
        cliente.telefone = telefone;
        cliente.email = email;
        cliente.tipo = tipo == 2 ? "VIP" : "Normal";
        cliente.dataInicio = LocalDate.now();
        
        clientes.add(cliente);
        System.out.println("\nCliente cadastrado! ID: " + cliente.id);
    }
    
    static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        for (Cliente c : clientes) {
            System.out.println("\nID: " + c.id);
            System.out.println("Nome: " + c.nome);
            System.out.println("CPF: " + c.cpf);
            System.out.println("Tipo: " + c.tipo);
            System.out.println("Telefone: " + c.telefone);
            System.out.println("------------------------");
        }
    }
    
    static void buscarCliente() {
        System.out.print("ID do cliente: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        Cliente cliente = clientes.stream()
            .filter(c -> c.id == id)
            .findFirst()
            .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
        } else {
            System.out.println("\n--- DADOS DO CLIENTE ---");
            System.out.println("ID: " + cliente.id);
            System.out.println("Nome: " + cliente.nome);
            System.out.println("CPF: " + cliente.cpf);
            System.out.println("Email: " + cliente.email);
            System.out.println("Telefone: " + cliente.telefone);
            System.out.println("Tipo: " + cliente.tipo);
            System.out.println("Data Inicio: " + cliente.dataInicio);
        }
    }
    
    static void excluirCliente() {
        System.out.print("ID do cliente: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        boolean removido = clientes.removeIf(c -> c.id == id);
        
        if (removido) {
            System.out.println("Cliente excluido!");
        } else {
            System.out.println("Cliente nao encontrado!");
        }
    }
    
    // ========== MENU AVALIACOES ==========
    static void menuAvaliacoes() {
        while (true) {
            System.out.println("\n===== AVALIACOES FISICAS =====");
            System.out.println("1. Registrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> registrarAvaliacao();
                case 2 -> listarAvaliacoes();
                case 3 -> buscarAvaliacao();
                case 0 -> { return; }
            }
        }
    }
    
    static void registrarAvaliacao() {
        System.out.println("\n--- REGISTRAR AVALIACAO ---");
        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        scanner.nextLine();
        
        Cliente cliente = clientes.stream()
            .filter(c -> c.id == clienteId)
            .findFirst()
            .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
            return;
        }
        
        System.out.print("Peso (kg): ");
        double peso = scanner.nextDouble();
        
        System.out.print("Altura (cm): ");
        double altura = scanner.nextDouble();
        scanner.nextLine();
        
        Avaliacao av = new Avaliacao();
        av.id = System.currentTimeMillis();
        av.clienteId = clienteId;
        av.clienteNome = cliente.nome;
        av.peso = peso;
        av.altura = altura;
        av.imc = peso / Math.pow(altura / 100, 2);
        av.dataAvaliacao = LocalDate.now();
        
        if (av.imc < 18.5) av.classificacao = "Abaixo do peso";
        else if (av.imc < 25) av.classificacao = "Peso normal";
        else if (av.imc < 30) av.classificacao = "Sobrepeso";
        else av.classificacao = "Obesidade";
        
        avaliacoes.add(av);
        System.out.println("\nAvaliacao registrada!");
        System.out.println("IMC: " + String.format("%.2f", av.imc));
        System.out.println("Classificacao: " + av.classificacao);
    }
    
    static void listarAvaliacoes() {
        System.out.println("\n--- LISTA DE AVALIACOES ---");
        if (avaliacoes.isEmpty()) {
            System.out.println("Nenhuma avaliacao cadastrada.");
            return;
        }
        
        for (Avaliacao av : avaliacoes) {
            System.out.println("\nID: " + av.id);
            System.out.println("Cliente: " + av.clienteNome);
            System.out.println("Peso: " + av.peso + " kg");
            System.out.println("Altura: " + av.altura + " cm");
            System.out.println("IMC: " + String.format("%.2f", av.imc));
            System.out.println("Classificacao: " + av.classificacao);
            System.out.println("Data: " + av.dataAvaliacao);
            System.out.println("------------------------");
        }
    }
    
    static void buscarAvaliacao() {
        System.out.print("ID da avaliacao: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        Avaliacao av = avaliacoes.stream()
            .filter(a -> a.id == id)
            .findFirst()
            .orElse(null);
        
        if (av == null) {
            System.out.println("Avaliacao nao encontrada!");
        } else {
            System.out.println("\n--- DADOS DA AVALIACAO ---");
            System.out.println("ID: " + av.id);
            System.out.println("Cliente: " + av.clienteNome);
            System.out.println("Peso: " + av.peso + " kg");
            System.out.println("Altura: " + av.altura + " cm");
            System.out.println("IMC: " + String.format("%.2f", av.imc));
            System.out.println("Classificacao: " + av.classificacao);
            System.out.println("Data: " + av.dataAvaliacao);
        }
    }
    
    // ========== MENU PLANOS ==========
    static void menuPlanos() {
        while (true) {
            System.out.println("\n===== PLANOS =====");
            System.out.println("1. Criar Plano");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Atualizar Plano VIP");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> criarPlano();
                case 2 -> listarPlanos();
                case 3 -> buscarPlano();
                case 4 -> atualizarPlanoVIP();
                case 0 -> { return; }
            }
        }
    }
    
    static void criarPlano() {
        System.out.println("\n--- CRIAR PLANO ---");
        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        scanner.nextLine();
        
        Cliente cliente = clientes.stream()
            .filter(c -> c.id == clienteId)
            .findFirst()
            .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
            return;
        }
        
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        
        System.out.print("Descricao do Treino: ");
        String treino = scanner.nextLine();
        
        System.out.print("Descricao da Dieta: ");
        String dieta = scanner.nextLine();
        
        System.out.print("Status (1-Ativo / 2-Pausado / 3-Concluido): ");
        int statusNum = scanner.nextInt();
        scanner.nextLine();
        
        String status = switch (statusNum) {
            case 2 -> "Pausado";
            case 3 -> "Concluido";
            default -> "Ativo";
        };
        
        Plano plano = new Plano();
        plano.id = System.currentTimeMillis();
        plano.clienteId = clienteId;
        plano.clienteNome = cliente.nome;
        plano.clienteTipo = cliente.tipo;
        plano.objetivo = objetivo;
        plano.descricaoTreino = treino;
        plano.descricaoDieta = dieta;
        plano.status = status;
        plano.dataInicio = LocalDate.now();
        
        planos.add(plano);
        System.out.println("\nPlano criado! ID: " + plano.id);
    }
    
    static void listarPlanos() {
        System.out.println("\n--- LISTA DE PLANOS ---");
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
            return;
        }
        
        for (Plano p : planos) {
            String vip = p.clienteTipo.equals("VIP") ? " [VIP]" : "";
            System.out.println("\nID: " + p.id);
            System.out.println("Cliente: " + p.clienteNome + vip);
            System.out.println("Objetivo: " + p.objetivo);
            System.out.println("Status: " + p.status);
            System.out.println("Data Inicio: " + p.dataInicio);
            System.out.println("------------------------");
        }
    }
    
    static void buscarPlano() {
        System.out.print("ID do plano: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        Plano plano = planos.stream()
            .filter(p -> p.id == id)
            .findFirst()
            .orElse(null);
        
        if (plano == null) {
            System.out.println("Plano nao encontrado!");
        } else {
            System.out.println("\n--- DETALHES DO PLANO ---");
            System.out.println("ID: " + plano.id);
            System.out.println("Cliente: " + plano.clienteNome + " (" + plano.clienteTipo + ")");
            System.out.println("Objetivo: " + plano.objetivo);
            System.out.println("Status: " + plano.status);
            System.out.println("\nTREINO:\n" + plano.descricaoTreino);
            System.out.println("\nDIETA:\n" + plano.descricaoDieta);
            System.out.println("\nData Inicio: " + plano.dataInicio);
        }
    }
    
    static void atualizarPlanoVIP() {
        System.out.print("ID do plano: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        Plano plano = planos.stream()
            .filter(p -> p.id == id)
            .findFirst()
            .orElse(null);
        
        if (plano == null) {
            System.out.println("Plano nao encontrado!");
            return;
        }
        
        if (!plano.clienteTipo.equals("VIP")) {
            System.out.println("Este plano nao e VIP!");
            return;
        }
        
        System.out.print("Nova descricao do Treino: ");
        plano.descricaoTreino = scanner.nextLine();
        
        System.out.print("Nova descricao da Dieta: ");
        plano.descricaoDieta = scanner.nextLine();
        
        System.out.println("\nPlano VIP atualizado!");
    }
    
    // ========== MENU CONSULTAS ==========
    static void menuConsultas() {
        while (true) {
            System.out.println("\n===== CONSULTAS =====");
            System.out.println("1. Agendar");
            System.out.println("2. Listar Todas");
            System.out.println("3. Listar VIP");
            System.out.println("4. Buscar por ID");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> agendarConsulta();
                case 2 -> listarConsultas();
                case 3 -> listarConsultasVIP();
                case 4 -> buscarConsulta();
                case 0 -> { return; }
            }
        }
    }
    
    static void agendarConsulta() {
        System.out.println("\n--- AGENDAR CONSULTA ---");
        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        scanner.nextLine();
        
        Cliente cliente = clientes.stream()
            .filter(c -> c.id == clienteId)
            .findFirst()
            .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
            return;
        }
        
        System.out.print("Profissional (1-Personal / 2-Nutricionista / 3-Ambos): ");
        int profNum = scanner.nextInt();
        scanner.nextLine();
        
        String profissional = switch (profNum) {
            case 2 -> "Nutricionista";
            case 3 -> "Ambos";
            default -> "Personal Trainer";
        };
        
        System.out.print("Data (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine();
        
        System.out.print("Horario (HH:mm): ");
        String horario = scanner.nextLine();
        
        Consulta consulta = new Consulta();
        consulta.id = System.currentTimeMillis();
        consulta.clienteId = clienteId;
        consulta.clienteNome = cliente.nome;
        consulta.clienteTipo = cliente.tipo;
        consulta.profissional = profissional;
        consulta.data = dataStr;
        consulta.horario = horario;
        consulta.status = "Agendada";
        
        consultas.add(consulta);
        System.out.println("\nConsulta agendada! ID: " + consulta.id);
    }
    
    static void listarConsultas() {
        System.out.println("\n--- LISTA DE CONSULTAS ---");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }
        
        for (Consulta c : consultas) {
            String vip = c.clienteTipo.equals("VIP") ? " [VIP]" : "";
            System.out.println("\nID: " + c.id);
            System.out.println("Cliente: " + c.clienteNome + vip);
            System.out.println("Profissional: " + c.profissional);
            System.out.println("Data: " + c.data);
            System.out.println("Horario: " + c.horario);
            System.out.println("Status: " + c.status);
            System.out.println("------------------------");
        }
    }
    
    static void listarConsultasVIP() {
        System.out.println("\n--- CONSULTAS VIP ---");
        boolean encontrou = false;
        
        for (Consulta c : consultas) {
            if (c.clienteTipo.equals("VIP")) {
                encontrou = true;
                System.out.println("\nID: " + c.id);
                System.out.println("Cliente: " + c.clienteNome + " [VIP]");
                System.out.println("Profissional: " + c.profissional);
                System.out.println("Data: " + c.data);
                System.out.println("Horario: " + c.horario);
                System.out.println("Status: " + c.status);
                System.out.println("------------------------");
            }
        }
        
        if (!encontrou) {
            System.out.println("Nenhuma consulta VIP agendada.");
        }
    }
    
    static void buscarConsulta() {
        System.out.print("ID da consulta: ");
        long id = scanner.nextLong();
        scanner.nextLine();
        
        Consulta consulta = consultas.stream()
            .filter(c -> c.id == id)
            .findFirst()
            .orElse(null);
        
        if (consulta == null) {
            System.out.println("Consulta nao encontrada!");
        } else {
            System.out.println("\n--- DADOS DA CONSULTA ---");
            System.out.println("ID: " + consulta.id);
            System.out.println("Cliente: " + consulta.clienteNome + " (" + consulta.clienteTipo + ")");
            System.out.println("Profissional: " + consulta.profissional);
            System.out.println("Data: " + consulta.data);
            System.out.println("Horario: " + consulta.horario);
            System.out.println("Status: " + consulta.status);
        }
    }
    
    // ========== MENU FINANCEIRO ==========
    static void menuFinanceiro() {
        while (true) {
            System.out.println("\n===== FINANCEIRO =====");
            System.out.println("1. Registrar Pagamento");
            System.out.println("2. Listar Pagamentos");
            System.out.println("3. Relatorio Mensal");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1 -> registrarPagamento();
                case 2 -> listarPagamentos();
                case 3 -> relatorioMensal();
                case 0 -> { return; }
            }
        }
    }
    
    static void registrarPagamento() {
        System.out.println("\n--- REGISTRAR PAGAMENTO ---");
        System.out.print("ID do Cliente: ");
        long clienteId = scanner.nextLong();
        scanner.nextLine();
        
        Cliente cliente = clientes.stream()
            .filter(c -> c.id == clienteId)
            .findFirst()
            .orElse(null);
        
        if (cliente == null) {
            System.out.println("Cliente nao encontrado!");
            return;
        }
        
        System.out.print("Servico: ");
        String servico = scanner.nextLine();
        
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        
        System.out.print("Status (1-Pago / 2-Pendente / 3-Atrasado): ");
        int statusNum = scanner.nextInt();
        scanner.nextLine();
        
        String status = switch (statusNum) {
            case 2 -> "Pendente";
            case 3 -> "Atrasado";
            default -> "Pago";
        };
        
        Pagamento pag = new Pagamento();
        pag.id = System.currentTimeMillis();
        pag.clienteId = clienteId;
        pag.clienteNome = cliente.nome;
        pag.servico = servico;
        pag.valor = valor;
        pag.status = status;
        pag.data = LocalDate.now();
        
        pagamentos.add(pag);
        System.out.println("\nPagamento registrado! ID: " + pag.id);
    }
    
    static void listarPagamentos() {
        System.out.println("\n--- LISTA DE PAGAMENTOS ---");
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        
        for (Pagamento p : pagamentos) {
            System.out.println("\nID: " + p.id);
            System.out.println("Cliente: " + p.clienteNome);
            System.out.println("Servico: " + p.servico);
            System.out.println("Valor: R$ " + String.format("%.2f", p.valor));
            System.out.println("Status: " + p.status);
            System.out.println("Data: " + p.data);
            System.out.println("------------------------");
        }
    }
    
    static void relatorioMensal() {
        System.out.println("\n--- RELATORIO MENSAL ---");
        
        LocalDate hoje = LocalDate.now();
        double totalPago = 0;
        double totalPendente = 0;
        double totalAtrasado = 0;
        
        for (Pagamento p : pagamentos) {
            if (p.data.getMonth() == hoje.getMonth() && 
                p.data.getYear() == hoje.getYear()) {
                
                if (p.status.equals("Pago")) {
                    totalPago += p.valor;
                } else if (p.status.equals("Pendente")) {
                    totalPendente += p.valor;
                } else if (p.status.equals("Atrasado")) {
                    totalAtrasado += p.valor;
                }
            }
        }
        
        System.out.println("\nTotal Recebido: R$ " + String.format("%.2f", totalPago));
        System.out.println("Total Pendente: R$ " + String.format("%.2f", totalPendente));
        System.out.println("Total Atrasado: R$ " + String.format("%.2f", totalAtrasado));
        System.out.println("\nTotal Geral: R$ " + String.format("%.2f", 
            totalPago + totalPendente + totalAtrasado));
    }
}

// ========== CLASSES DE MODELO ==========

class Cliente {
    long id;
    String nome;
    String cpf;
    String telefone;
    String email;
    String tipo; // Normal ou VIP
    LocalDate dataInicio;
}

class Avaliacao {
    long id;
    long clienteId;
    String clienteNome;
    double peso;
    double altura;
    double imc;
    String classificacao;
    LocalDate dataAvaliacao;
}

class Plano {
    long id;
    long clienteId;
    String clienteNome;
    String clienteTipo;
    String objetivo;
    String descricaoTreino;
    String descricaoDieta;
    String status;
    LocalDate dataInicio;
}

class Consulta {
    long id;
    long clienteId;
    String clienteNome;
    String clienteTipo;
    String profissional;
    String data;
    String horario;
    String status;
}

class Pagamento {
    long id;
    long clienteId;
    String clienteNome;
    String servico;
    double valor;
    String status;
    LocalDate data;
}