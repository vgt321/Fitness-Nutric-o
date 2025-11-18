// ============================================
// SCRIPT.JS - FUNÇÕES GLOBAIS DO SISTEMA
// Sistema Vida Saudável - Fitness e Nutrição
// ============================================

// ====== FUNÇÕES DE FORMATAÇÃO ======

/**
 * Formata uma data no padrão brasileiro (DD/MM/AAAA)
 * @param {string} data - Data no formato YYYY-MM-DD
 * @returns {string} Data formatada
 */
function formatarData(data) {
    if (!data) return 'Não definida';
    const d = new Date(data + 'T00:00:00');
    return d.toLocaleDateString('pt-BR');
}

/**
 * Formata um valor monetário no padrão brasileiro
 * @param {number} valor - Valor numérico
 * @returns {string} Valor formatado (R$ 0,00)
 */
function formatarMoeda(valor) {
    if (valor === null || valor === undefined) return 'R$ 0,00';
    return `R$ ${valor.toFixed(2).replace('.', ',')}`;
}

/**
 * Formata data e hora completa
 * @param {string} dataISO - Data no formato ISO
 * @returns {string} Data e hora formatadas
 */
function formatarDataHora(dataISO) {
    if (!dataISO) return 'Não definida';
    const d = new Date(dataISO);
    return d.toLocaleString('pt-BR');
}

// ====== FUNÇÕES DE VALIDAÇÃO ======

/**
 * Valida se um CPF tem formato válido
 * @param {string} cpf - CPF a ser validado
 * @returns {boolean} True se válido
 */
function validarCPF(cpf) {
    cpf = cpf.replace(/[^\d]/g, '');
    return cpf.length === 11;
}

/**
 * Valida se um email tem formato válido
 * @param {string} email - Email a ser validado
 * @returns {boolean} True se válido
 */
function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

/**
 * Valida se um campo está vazio
 * @param {string} valor - Valor a ser validado
 * @returns {boolean} True se válido (não vazio)
 */
function validarCampoObrigatorio(valor) {
    return valor && valor.trim().length > 0;
}

// ====== FUNÇÕES DE MÁSCARA ======

/**
 * Aplica máscara de CPF (000.000.000-00)
 * @param {string} cpf - CPF sem máscara
 * @returns {string} CPF com máscara
 */
function mascararCPF(cpf) {
    cpf = cpf.replace(/\D/g, '');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    return cpf;
}

/**
 * Aplica máscara de telefone (00) 00000-0000
 * @param {string} telefone - Telefone sem máscara
 * @returns {string} Telefone com máscara
 */
function mascararTelefone(telefone) {
    telefone = telefone.replace(/\D/g, '');
    telefone = telefone.replace(/^(\d{2})(\d)/g, '($1) $2');
    telefone = telefone.replace(/(\d)(\d{4})$/, '$1-$2');
    return telefone;
}

// ====== FUNÇÕES DE CÁLCULO ======

/**
 * Calcula o IMC (Índice de Massa Corporal)
 * @param {number} peso - Peso em kg
 * @param {number} altura - Altura em cm
 * @returns {number} IMC calculado
 */
function calcularIMC(peso, altura) {
    if (!peso || !altura || altura === 0) return 0;
    const alturaMetros = altura / 100;
    return peso / (alturaMetros * alturaMetros);
}

/**
 * Classifica o IMC em categorias
 * @param {number} imc - Valor do IMC
 * @returns {string} Classificação do IMC
 */
function classificarIMC(imc) {
    if (imc < 18.5) return '⚠️ Abaixo do peso';
    if (imc < 25) return '✅ Peso normal';
    if (imc < 30) return '⚠️ Sobrepeso';
    if (imc < 35) return '🔴 Obesidade Grau I';
    if (imc < 40) return '🔴 Obesidade Grau II';
    return '🔴 Obesidade Grau III';
}

/**
 * Calcula a idade a partir da data de nascimento
 * @param {string} dataNascimento - Data no formato YYYY-MM-DD
 * @returns {number} Idade em anos
 */
function calcularIdade(dataNascimento) {
    if (!dataNascimento) return 0;
    const hoje = new Date();
    const nascimento = new Date(dataNascimento + 'T00:00:00');
    let idade = hoje.getFullYear() - nascimento.getFullYear();
    const mes = hoje.getMonth() - nascimento.getMonth();
    if (mes < 0 || (mes === 0 && hoje.getDate() < nascimento.getDate())) {
        idade--;
    }
    return idade;
}

// ====== FUNÇÕES DE ARMAZENAMENTO ======

/**
 * Busca dados do localStorage
 * @param {string} chave - Chave do localStorage
 * @returns {Array} Array com os dados ou array vazio
 */
function buscarDados(chave) {
    try {
        const dados = localStorage.getItem(chave);
        return dados ? JSON.parse(dados) : [];
    } catch (error) {
        console.error(`Erro ao buscar dados de ${chave}:`, error);
        return [];
    }
}

/**
 * Salva dados no localStorage
 * @param {string} chave - Chave do localStorage
 * @param {Array} dados - Dados a serem salvos
 * @returns {boolean} True se salvou com sucesso
 */
function salvarDados(chave, dados) {
    try {
        localStorage.setItem(chave, JSON.stringify(dados));
        return true;
    } catch (error) {
        console.error(`Erro ao salvar dados em ${chave}:`, error);
        return false;
    }
}

/**
 * Busca um item por ID
 * @param {string} chave - Chave do localStorage
 * @param {number} id - ID do item
 * @returns {Object|null} Item encontrado ou null
 */
function buscarPorId(chave, id) {
    const dados = buscarDados(chave);
    return dados.find(item => item.id === id) || null;
}

/**
 * Exclui um item por ID
 * @param {string} chave - Chave do localStorage
 * @param {number} id - ID do item
 * @returns {boolean} True se excluiu com sucesso
 */
function excluirPorId(chave, id) {
    const dados = buscarDados(chave);
    const novosDados = dados.filter(item => item.id !== id);
    return salvarDados(chave, novosDados);
}

/**
 * Atualiza um item por ID
 * @param {string} chave - Chave do localStorage
 * @param {number} id - ID do item
 * @param {Object} novoItem - Novo objeto
 * @returns {boolean} True se atualizou com sucesso
 */
function atualizarPorId(chave, id, novoItem) {
    const dados = buscarDados(chave);
    const novosDados = dados.map(item => item.id === id ? novoItem : item);
    return salvarDados(chave, novosDados);
}

// ====== FUNÇÕES DE INTERFACE ======

/**
 * Mostra mensagem de alerta na tela
 * @param {string} texto - Texto da mensagem
 * @param {string} tipo - Tipo: 'success', 'danger', 'info', 'warning'
 * @param {number} duracao - Duração em ms (padrão: 3000)
 */
function mostrarMensagem(texto, tipo = 'info', duracao = 3000) {
    let mensagemDiv = document.getElementById('mensagem');
    
    // Se não existir div de mensagem, cria uma
    if (!mensagemDiv) {
        mensagemDiv = document.createElement('div');
        mensagemDiv.id = 'mensagem';
        mensagemDiv.style.position = 'fixed';
        mensagemDiv.style.top = '20px';
        mensagemDiv.style.right = '20px';
        mensagemDiv.style.zIndex = '9999';
        mensagemDiv.style.minWidth = '300px';
        mensagemDiv.style.maxWidth = '500px';
        document.body.appendChild(mensagemDiv);
    }
    
    const classes = {
        success: 'alert alert-success',
        danger: 'alert alert-danger',
        info: 'alert alert-info',
        warning: 'alert alert-warning'
    };
    
    mensagemDiv.className = classes[tipo] || classes.info;
    mensagemDiv.textContent = texto;
    mensagemDiv.style.display = 'block';
    
    setTimeout(() => {
        mensagemDiv.style.display = 'none';
    }, duracao);
}

/**
 * Confirma uma ação com o usuário
 * @param {string} mensagem - Mensagem de confirmação
 * @returns {boolean} True se confirmou
 */
function confirmarAcao(mensagem) {
    return confirm(mensagem);
}

/**
 * Limpa todos os campos de um formulário
 * @param {string} formId - ID do formulário
 */
function limparFormulario(formId) {
    const form = document.getElementById(formId);
    if (form) {
        form.reset();
    }
}

// ====== FUNÇÕES DE BUSCA E FILTRO ======

/**
 * Filtra array por termo de busca
 * @param {Array} array - Array a ser filtrado
 * @param {string} termo - Termo de busca
 * @param {Array} campos - Campos a serem pesquisados
 * @returns {Array} Array filtrado
 */
function filtrarPorTermo(array, termo, campos) {
    if (!termo || termo.trim() === '') return array;
    
    termo = termo.toLowerCase();
    
    return array.filter(item => {
        return campos.some(campo => {
            const valor = item[campo];
            if (valor === null || valor === undefined) return false;
            return valor.toString().toLowerCase().includes(termo);
        });
    });
}

/**
 * Ordena array por campo
 * @param {Array} array - Array a ser ordenado
 * @param {string} campo - Campo para ordenação
 * @param {string} ordem - 'asc' ou 'desc'
 * @returns {Array} Array ordenado
 */
function ordenarPor(array, campo, ordem = 'asc') {
    return array.sort((a, b) => {
        const valorA = a[campo];
        const valorB = b[campo];
        
        if (ordem === 'asc') {
            return valorA > valorB ? 1 : -1;
        } else {
            return valorA < valorB ? 1 : -1;
        }
    });
}

// ====== FUNÇÕES DE ESTATÍSTICAS ======

/**
 * Calcula totais e médias de um array
 * @param {Array} array - Array de números
 * @returns {Object} Objeto com total, média, min, max
 */
function calcularEstatisticas(array) {
    if (!array || array.length === 0) {
        return { total: 0, media: 0, min: 0, max: 0, quantidade: 0 };
    }
    
    const total = array.reduce((sum, val) => sum + val, 0);
    const media = total / array.length;
    const min = Math.min(...array);
    const max = Math.max(...array);
    
    return {
        total,
        media: parseFloat(media.toFixed(2)),
        min,
        max,
        quantidade: array.length
    };
}

/**
 * Conta ocorrências em um array
 * @param {Array} array - Array a ser analisado
 * @param {string} campo - Campo a ser contado
 * @returns {Object} Objeto com contagem
 */
function contarOcorrencias(array, campo) {
    const contagem = {};
    
    array.forEach(item => {
        const valor = item[campo];
        contagem[valor] = (contagem[valor] || 0) + 1;
    });
    
    return contagem;
}

// ====== FUNÇÕES DE DATA ======

/**
 * Retorna a data de hoje no formato YYYY-MM-DD
 * @returns {string} Data de hoje
 */
function obterDataHoje() {
    const hoje = new Date();
    return hoje.toISOString().split('T')[0];
}

/**
 * Verifica se uma data está no passado
 * @param {string} data - Data no formato YYYY-MM-DD
 * @returns {boolean} True se está no passado
 */
function estaNoPassado(data) {
    const dataVerificar = new Date(data + 'T00:00:00');
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);
    return dataVerificar < hoje;
}

/**
 * Calcula diferença em dias entre duas datas
 * @param {string} data1 - Data inicial
 * @param {string} data2 - Data final
 * @returns {number} Diferença em dias
 */
function diferencaDias(data1, data2) {
    const d1 = new Date(data1 + 'T00:00:00');
    const d2 = new Date(data2 + 'T00:00:00');
    const diff = Math.abs(d2 - d1);
    return Math.floor(diff / (1000 * 60 * 60 * 24));
}

// ====== FUNÇÕES DE EXPORTAÇÃO ======

/**
 * Exporta dados para CSV
 * @param {Array} dados - Array de objetos
 * @param {string} nomeArquivo - Nome do arquivo
 */
function exportarParaCSV(dados, nomeArquivo) {
    if (!dados || dados.length === 0) {
        mostrarMensagem('Não há dados para exportar!', 'warning');
        return;
    }
    
    const headers = Object.keys(dados[0]);
    let csv = headers.join(',') + '\n';
    
    dados.forEach(obj => {
        const valores = headers.map(header => {
            const valor = obj[header];
            return typeof valor === 'string' && valor.includes(',') 
                ? `"${valor}"` 
                : valor;
        });
        csv += valores.join(',') + '\n';
    });
    
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = nomeArquivo + '.csv';
    link.click();
    
    mostrarMensagem('Dados exportados com sucesso!', 'success');
}

// ====== FUNÇÕES DE DEBUG ======

/**
 * Loga informações no console (apenas em desenvolvimento)
 * @param {string} mensagem - Mensagem de log
 * @param {*} dados - Dados adicionais
 */
function log(mensagem, dados = null) {
    console.log(`[Sistema Vida Saudável] ${mensagem}`, dados || '');
}

/**
 * Limpa todos os dados do localStorage (CUIDADO!)
 * @returns {boolean} True se limpou
 */
function limparTodosDados() {
    if (confirmarAcao('⚠️ ATENÇÃO! Isso vai apagar TODOS os dados do sistema. Tem certeza?')) {
        localStorage.clear();
        mostrarMensagem('Todos os dados foram apagados!', 'success');
        setTimeout(() => location.reload(), 1500);
        return true;
    }
    return false;
}

// ====== INICIALIZAÇÃO ======

// Log de carregamento
document.addEventListener('DOMContentLoaded', function() {
    log('Script global carregado com sucesso! ✅');
});

// Avisar usuário se o localStorage estiver cheio
window.addEventListener('storage', function(e) {
    if (e.key === null) {
        mostrarMensagem('⚠️ Armazenamento local está cheio!', 'danger', 5000);
    }
}); 