package Extrator.extratorLSG.api.readers;

import Extrator.extratorLSG.api.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;


public class extratorLSG extends DefaultHandler {
    private String tagAtual;
    public Curriculo cur;
    public ArrayList<Artigo> artigos;
    public Artigo lastArtigo;
    public ArrayList<TrabalhoEvento> evento;
    public TrabalhoEvento lastEvento;
    public ArrayList<CapituloELivro> capitulo;
    public CapituloELivro lastCapitulo;
    public ArrayList<Formacao> formacao;
    public Formacao lastFormacao;
    public ArrayList<Projeto> projeto;
    public Projeto lastProjeto;
    public ArrayList<Orientacao> orientacao;
    public Orientacao lastOrientacao;
    public ArrayList<Tecnica> tecnica;
    public Tecnica lastTecnica;
    public String arquivo;

    /**
     * construtor default
     */
    public extratorLSG(String arquivo) {
        super();
        this.cur = new Curriculo();
        this.artigos = new ArrayList<>();
        this.evento = new ArrayList<>();
        this.capitulo = new ArrayList<>();
        this.formacao = new ArrayList<>();
        this.projeto = new ArrayList<>();
        this.orientacao = new ArrayList<>();
        this.tecnica = new ArrayList<>();
        this.arquivo = arquivo;
    }


    public void fazerParsing(String pathArq) {

        // Passo 1: cria instÃ¢ncia da classe SAXParser, atravÃ©s da factory
        // SAXParserFactory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;

        try {
            saxParser = factory.newSAXParser();

            // Passo 2: comanda o inÃ­cio do parsing
            saxParser.parse(pathArq, this); // o "this" indica que a prÃ³pria
            // classe "DevmediaSAX" atuarÃ¡ como
            // gerenciadora de eventos SAX.

            // Passo 3: tratamento de exceÃ§Ãµes.
        } catch (ParserConfigurationException | SAXException | IOException e) {
            StringBuffer msg = new StringBuffer();
            msg.append("Erro:\n");
            msg.append(e.getMessage() + "\n");
            msg.append(e.toString());
            System.out.println(msg);
        }
    }

    // os mÃ©todos startDocument, endDocument, startElement, endElement e
    // characters, listados a seguir, representam os mÃ©todos de call-back da API
    // SAX

    /**
     * evento startDocument do SAX. Disparado antes do processamento da primeira
     * linha
     */
    public void startDocument() {
        //System.out.println("\nIniciando o Parsing...\n");
    }

    /**
     * evento endDocument do SAX. Disparado depois do processamento da Ãºltima
     * linha
     */
    public void endDocument() {
        //System.out.println("\nFim do Parsing...");
    }

    /**
     * evento startElement do SAX. disparado quando o processador SAX identifica
     * a abertura de uma tag. Ele possibilita a captura do nome da tag e dos
     * nomes e valores de todos os atributos associados a esta tag, caso eles
     * existam.
     */
    public void startElement(String uri, String localName, String qName,
                             Attributes atts) {

        if (qName.compareTo("CURRICULO-VITAE") == 0) {
            cur.DATA_ATUALIZACAO = atts.getValue("DATA-ATUALIZACAO");
            cur.NUMERO_IDENTIFICADOR = atts.getValue("NUMERO-IDENTIFICADOR");
            if (cur.NUMERO_IDENTIFICADOR == null || cur.NUMERO_IDENTIFICADOR.isEmpty())
                cur.NUMERO_IDENTIFICADOR = arquivo;
        }
        if (qName.compareTo("ENDERECO-PROFISSIONAL") == 0) {
            cur.NOME_INSTITUICAO_EMPRESA = atts.getValue("NOME-INSTITUICAO-EMPRESA");
            cur.NOME_ORGAO = atts.getValue("NOME-ORGAO");
            cur.DDD = atts.getValue("DDD");
            cur.TELEFONE = atts.getValue("TELEFONE");
            cur.HOMEPAGE = atts.getValue("HOME-PAGE");
        }
        // se a tag for "", recupera o valor do atributo "sigla"
        if (qName.compareTo("DADOS-GERAIS") == 0) {

            cur.NOME_COMPLETO = atts.getValue("NOME-COMPLETO");
            cur.NOME_EM_CITACOES_BIBLIOGRAFICAS = atts.getValue("NOME-EM-CITACOES-BIBLIOGRAFICAS");
            /****/
            System.out.print("Processando: " + cur.NOME_COMPLETO + " .... ");
            /******/
        }

        if (qName.compareTo("RESUMO-CV") ==0){
            cur.TEXTO_RESUMO_CV_RH = atts.getValue("TEXTO-RESUMO-CV-RH");
        }

        if (qName.compareTo("GRADUACAO") == 0) {
            lastFormacao = new Formacao();
            lastFormacao.tipo_formacao = "GRADUACAO";
            lastFormacao.titulo_trabalho = atts.getValue("TITULO-DO-TRABALHO-DE-CONCLUSAO-DE-CURSO");
            lastFormacao.nome_orientador = atts.getValue("NOME-DO-ORIENTADOR");
            lastFormacao.nome_instituicao = atts.getValue("NOME-INSTITUICAO");
            lastFormacao.nome_orgao = atts.getValue("NOME-ORGAO");
            lastFormacao.nome_curso = atts.getValue("NOME-CURSO");
            lastFormacao.ano_inicio = atts.getValue("ANO-DE-INICIO");
            lastFormacao.ano_conclusao = atts.getValue("ANO-DE-CONCLUSAO");
            formacao.add(lastFormacao);
        }

        if (qName.compareTo("MESTRADO") == 0) {
            lastFormacao = new Formacao();
            lastFormacao.tipo_formacao = "MESTRADO";
            lastFormacao.titulo_trabalho = atts.getValue("TITULO-DA-DISSERTACAO-TESE");
            lastFormacao.nome_orientador = atts.getValue("NOME-COMPLETO-DO-ORIENTADOR");
            lastFormacao.nome_co_orientador = atts.getValue("NOME-DO-CO-ORIENTADOR");
            lastFormacao.nome_instituicao = atts.getValue("NOME-INSTITUICAO");
            lastFormacao.nome_orgao = atts.getValue("NOME-ORGAO");
            lastFormacao.nome_curso = atts.getValue("NOME-CURSO");
            lastFormacao.ano_inicio = atts.getValue("ANO-DE-INICIO");
            lastFormacao.ano_conclusao = atts.getValue("ANO-DE-CONCLUSAO");
            lastFormacao.ano_obtencao_titulo = atts.getValue("ANO-DE-OBTENCAO-DO-TITULO");
            formacao.add(lastFormacao);
        }


        if (qName.compareTo("DOUTORADO") == 0) {
            lastFormacao = new Formacao();
            lastFormacao.tipo_formacao = "DOUTORADO";
            lastFormacao.titulo_trabalho = atts.getValue("TITULO-DA-DISSERTACAO-TESE");
            lastFormacao.nome_orientador = atts.getValue("NOME-COMPLETO-DO-ORIENTADOR");
            lastFormacao.nome_co_orientador = atts.getValue("NOME-DO-CO-ORIENTADOR");
            lastFormacao.nome_instituicao = atts.getValue("NOME-INSTITUICAO");
            lastFormacao.nome_orgao = atts.getValue("NOME-ORGAO");
            lastFormacao.nome_curso = atts.getValue("NOME-CURSO");
            lastFormacao.ano_inicio = atts.getValue("ANO-DE-INICIO");
            lastFormacao.ano_conclusao = atts.getValue("ANO-DE-CONCLUSAO");
            lastFormacao.ano_obtencao_titulo = atts.getValue("ANO-DE-OBTENCAO-DO-TITULO");
            formacao.add(lastFormacao);
        }


        if ((qName.compareTo("ARTIGO-PUBLICADO")==0) ||
                (qName.compareTo("ARTIGO-ACEITO-PARA-PUBLICACAO")==0)){
            tagAtual = qName;
            if (lastArtigo == null)
                lastArtigo = new Artigo();
            lastArtigo.SEQUENCIA_PRODUCAO = atts.getValue("SEQUENCIA-PRODUCAO");
            lastArtigo.TIPO = tagAtual;
        }

        if (qName.compareTo("DADOS-BASICOS-DO-ARTIGO")==0) {
            if (lastArtigo == null)
                lastArtigo = new Artigo();

            lastArtigo.TITULO_DO_ARTIGO = atts.getValue("TITULO-DO-ARTIGO");
            lastArtigo.ANO_DO_ARTIGO = atts.getValue("ANO-DO-ARTIGO");
            lastArtigo.DOI = atts.getValue("DOI");
            lastArtigo.NATUREZA = atts.getValue("NATUREZA");
        }

        if (qName.compareTo("DETALHAMENTO-DO-ARTIGO")==0) {
            if (lastArtigo == null)
                lastArtigo = new Artigo();

            lastArtigo.TITULO_DO_PERIODICO_OU_REVISTA = atts.getValue("TITULO-DO-PERIODICO-OU-REVISTA");
            lastArtigo.ISSN = atts.getValue("ISSN");
            lastArtigo.VOLUME = atts.getValue("VOLUME");
            lastArtigo.FASCICULO = atts.getValue("FASCICULO");
            lastArtigo.SERIE = atts.getValue("SERIE");
            lastArtigo.PAGINA_FINAL = atts.getValue("PAGINA-FINAL");
            lastArtigo.PAGINA_INICIAL = atts.getValue("PAGINA-INICIAL");
        }

        if (qName.compareTo("TRABALHO-EM-EVENTOS")==0) {
            tagAtual = qName;
            if (lastEvento == null)
                lastEvento = new TrabalhoEvento();
            lastEvento.SEQUENCIA_PRODUCAO = atts.getValue("SEQUENCIA-PRODUCAO");
        }

        if (qName.compareTo("DADOS-BASICOS-DO-TRABALHO")==0) {
            if (lastEvento == null)
                lastEvento = new TrabalhoEvento();

            lastEvento.TITULO_DO_TRABALHO= atts.getValue("TITULO-DO-TRABALHO");

            //System.out.println("" + lastEvento.TITULO_DO_TRABALHO);

            lastEvento.ANO_DO_TRABALHO = atts.getValue("ANO-DO-TRABALHO");
            lastEvento.DOI = atts.getValue("DOI");
            lastEvento.NATUREZA = atts.getValue("NATUREZA");
            lastEvento.PAIS_DO_EVENTO = atts.getValue("PAIS-DO-EVENTO");

        }

        if (qName.compareTo("DETALHAMENTO-DO-TRABALHO")==0) {
            if (lastEvento == null)
                lastEvento = new TrabalhoEvento();

            lastEvento.TITULO_DOS_ANAIS_OU_PROCEEDINGS = atts.getValue("TITULO-DOS-ANAIS-OU-PROCEEDINGS");
            lastEvento.ISBN = atts.getValue("ISBN");
            lastEvento.VOLUME = atts.getValue("VOLUME");
            lastEvento.FASCICULO = atts.getValue("FASCICULO");
            lastEvento.SERIE = atts.getValue("SERIE");
            lastEvento.PAGINA_FINAL = atts.getValue("PAGINA-FINAL");
            lastEvento.PAGINA_INICIAL = atts.getValue("PAGINA-INICIAL");
            lastEvento.CLASSIFICACAO_DO_EVENTO = atts.getValue("CLASSIFICACAO-DO-EVENTO");
            lastEvento.NOME_DO_EVENTO = atts.getValue("NOME-DO-EVENTO");
            lastEvento.CIDADE_DO_EVENTO = atts.getValue("CIDADE-DO-EVENTO");
            lastEvento.NOME_DA_EDITORA = atts.getValue("NOME-DA-EDITORA");
        }

        if (qName.compareTo("CAPITULO-DE-LIVRO-PUBLICADO")==0 ||
                qName.compareTo("LIVRO-PUBLICADO-OU-ORGANIZADO")==0) {
            tagAtual = qName;
            if (lastCapitulo == null)
                lastCapitulo = new CapituloELivro();
            lastCapitulo.SEQUENCIA_PRODUCAO = atts.getValue("SEQUENCIA-PRODUCAO");
        }

        if (qName.compareTo("DADOS-BASICOS-DO-CAPITULO")==0 ||
                qName.compareTo("DADOS-BASICOS-DO-LIVRO")==0) {
            if (lastCapitulo == null)
                lastCapitulo = new CapituloELivro();

            lastCapitulo.TITULO_DO_CAPITULO_DO_LIVRO= atts.getValue("TITULO-DO-CAPITULO-DO-LIVRO");
            lastCapitulo.ANO = atts.getValue("ANO");
            lastCapitulo.DOI = atts.getValue("DOI");
            lastCapitulo.TIPO = atts.getValue("TIPO");
            lastCapitulo.PAIS_DE_PUBLICACAO = atts.getValue("PAIS-DE-PUBLICACAO");

        }

        if (qName.compareTo("DETALHAMENTO-DO-CAPITULO")==0 ||
                qName.compareTo("DETALHAMENTO-DO-LIVRO")==0) {
            if (lastCapitulo == null)
                lastCapitulo = new CapituloELivro();

            lastCapitulo.TITULO_DO_LIVRO = atts.getValue("TITULO-DO-LIVRO");
            lastCapitulo.ISBN = atts.getValue("ISBN");
            lastCapitulo.NUMERO_DE_VOLUMES = atts.getValue("NUMERO-DE-VOLUMES");
            lastCapitulo.ORGANIZADORES = atts.getValue("ORGANIZADORES");
            lastCapitulo.NUMERO_DA_SERIE = atts.getValue("NUMERO-DA-SERIE");
            lastCapitulo.PAGINA_FINAL = atts.getValue("PAGINA-FINAL");
            lastCapitulo.PAGINA_INICIAL = atts.getValue("PAGINA-INICIAL");
            lastCapitulo.NOME_DA_EDITORA = atts.getValue("NOME-DA-EDITORA");
            lastCapitulo.CIDADE_DA_EDITORA = atts.getValue("CIDADE-DA-EDITORA");
        }


        if (qName.compareTo("AUTORES") == 0) {
            if (lastEvento!=null) {
                //if (lastEvento == null) lastEvento = new TrabalhoEvento();
                lastEvento.AUTORES += atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";";
            }

            if (lastArtigo!=null) {
                //if (lastArtigo == null) lastArtigo = new Artigo();
                lastArtigo.AUTORES += atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";";
            }
            if (lastCapitulo!=null) {
                //if (lastCapitulo == null) lastCapitulo = new CapituloLivro();
                lastCapitulo.AUTORES += atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";";
            }
            if (lastTecnica != null) {
                lastTecnica.autores += atts.getValue("NOME-COMPLETO-DO-AUTOR") + ";";
            }

        }

        if (qName.compareTo("PROJETO-DE-PESQUISA") == 0) {
            if (lastProjeto == null)
                lastProjeto = new Projeto();

            lastProjeto.SEQUENCIA_PROJETO=atts.getValue("SEQUENCIA-PROJETO");
            lastProjeto.ANO_INICIO=atts.getValue("ANO-INICIO");
            lastProjeto.ANO_FIM=atts.getValue("ANO-FIM");
            lastProjeto.NOME_DO_PROJETO=atts.getValue("NOME-DO-PROJETO");
            lastProjeto.SITUACAO=atts.getValue("SITUACAO");
            lastProjeto.NATUREZA=atts.getValue("NATUREZA");
            lastProjeto.NUMERO_GRADUACAO=atts.getValue("NUMERO-GRADUACAO");
            lastProjeto.NUMERO_ESPECIALIZACAO=atts.getValue("NUMERO-ESPECIALIZACAO");
            lastProjeto.NUMERO_MESTRADO_ACADEMICO=atts.getValue("NUMERO-MESTRADO-ACADEMICO");
            lastProjeto.NUMERO_MESTRADO_PROF=atts.getValue("NUMERO-MESTRADO-PROF");
            lastProjeto.NUMERO_DOUTORADO=atts.getValue("NUMERO-DOUTORADO");
            lastProjeto.DESCRICAO_DO_PROJETO=atts.getValue("DESCRICAO-DO-PROJETO");

        }
        if (qName.compareTo("INTEGRANTES-DO-PROJETO") == 0) {
            if (lastProjeto == null)
                lastProjeto = new Projeto();

            lastProjeto.integrantes += atts.getValue("NOME-COMPLETO") + ";  ";
            String resp = atts.getValue("FLAG-RESPONSAVEL");
            if ((resp.compareTo("SIM")==0) && (cur.NOME_COMPLETO.compareTo(atts.getValue("NOME-COMPLETO")) ==0))
                lastProjeto.isReponsavel = true;
        }
        if (qName.compareTo("FINANCIADOR-DO-PROJETO") == 0) {
            if (lastProjeto == null)
                lastProjeto = new Projeto();
            Financiador f = new Financiador();
            f.SEQUENCIA_FINANCIADOR = atts.getValue("SEQUENCIA-FINANCIADOR");
            f.NOME_INSTITUICAO = atts.getValue("NOME-INSTITUICAO");
            f.NATUREZA = atts.getValue("NATUREZA");
            lastProjeto.finaciamento.add(f);
        }

        if (qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") == 0 ||
                qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO") ==0 ||
                qName.compareTo("OUTRAS-ORIENTACOES-CONCLUIDAS")==0 ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO") ==0  ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0)  {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            lastOrientacao.sequencia_orientacao = atts.getValue("SEQUENCIA-PRODUCAO");
            if (qName.contains("CONCLUIDAS"))
                lastOrientacao.is_finalizado = true;
        }
        if (qName.compareTo("DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO")==0 ||
                qName.compareTo("DADOS-BASICOS-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")==0 ||
                qName.compareTo("DADOS-BASICOS-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")==0 ||
                qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO")==0 ||
                qName.compareTo("DADOS-BASICOS-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0) {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            lastOrientacao.natureza = atts.getValue("NATUREZA");
            lastOrientacao.tipo = atts.getValue("TIPO");

            lastOrientacao.titulo = atts.getValue("TITULO");
            lastOrientacao.ano = atts.getValue("ANO");
            lastOrientacao.doi = atts.getValue("DOI");
        }
        if (qName.compareTo("DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") ==0 ||
                qName.compareTo("DETALHAMENTO-DE-ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO")==0 ||
                qName.compareTo("DETALHAMENTO-DE-OUTRAS-ORIENTACOES-CONCLUIDAS")==0 ||
                qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO")==0 ||
                qName.compareTo("DETALHAMENTO-DA-ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0) {
            if (lastOrientacao == null)
                lastOrientacao = new Orientacao();
            lastOrientacao.tipo_orientacao = atts.getValue("TIPO-DE-ORIENTACAO");
            lastOrientacao.nome_orientando = atts.getValue("NOME-DO-ORIENTADO");
            lastOrientacao.nome_instituicao = atts.getValue("NOME-DA-INSTITUICAO");
            lastOrientacao.nome_orgao = atts.getValue("NOME-ORGAO");
            lastOrientacao.nome_curso = atts.getValue("NOME-DO-CURSO");
        }

        if (qName.compareTo("SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.tipo = qName;
            lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DO-SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.titulo = atts.getValue("TITULO-DO-SOFTWARE");
            lastTecnica.ano = atts.getValue("ANO");
        }
        if (qName.compareTo("DETALHAMENTO-DO-SOFTWARE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.outras_informacoes += "Finalidade: " + atts.getValue("FINALIDADE") + "; ";
            lastTecnica.outras_informacoes += "Plataforma: " + atts.getValue("PLATAFORMA") + "; ";
            lastTecnica.outras_informacoes += "Ambiente: " + atts.getValue("AMBIENTE") + "; ";
            lastTecnica.financiadora += atts.getValue("INSTITUICAO-FINANCIADORA");
        }

        if (qName.compareTo("PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.tipo = qName;
            lastTecnica.sequencia_producao = atts.getValue("SEQUENCIA-PRODUCAO");
        }
        if (qName.compareTo("DADOS-BASICOS-DA-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.titulo = atts.getValue("TITULO");
            lastTecnica.ano = atts.getValue("ANO-DESENVOLVIMENTO");
        }
        if (qName.compareTo("DETALHAMENTO-DA-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();
            lastTecnica.outras_informacoes += "Finalidade: " + atts.getValue("FINALIDADE") + "; ";
            lastTecnica.financiadora += atts.getValue("INSTITUICAO-FINANCIADORA");
        }
        if (qName.compareTo("REGISTRO-OU-PATENTE")==0) {
            if (lastTecnica == null)
                lastTecnica = new Tecnica();

            lastTecnica.outras_informacoes += "Tipo Registro/Patente: " + atts.getValue("TIPO-PATENTE") + "; ";
            lastTecnica.outras_informacoes += "CÃ³digo: " + atts.getValue("CODIGO-DO-REGISTRO-OU-PATENTE") + "; ";
            lastTecnica.outras_informacoes += "Data DepÃ³sito: " + atts.getValue("DATA-PEDIDO-DE-DEPOSITO") + "; ";
            lastTecnica.outras_informacoes += "InstituiÃ§Ã£o: " + atts.getValue("INSTITUICAO-DEPOSITO-REGISTRO") + "; ";
            lastTecnica.outras_informacoes += "Depositante: " + atts.getValue("NOME-DO-DEPOSITANTE") + "; ";
            lastTecnica.outras_informacoes += "Titular: " + atts.getValue("NOME-DO-TITULAR") + "; ";
        }
    }

    /**
     * evento endElement do SAX. Disparado quando o processador SAX identifica o
     * fechamento de uma tag (ex: )
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        tagAtual = "";

        if ((qName.compareTo("ARTIGO-PUBLICADO") == 0) ||
                (qName.compareTo("ARTIGO-ACEITO-PARA-PUBLICACAO") == 0)){
            artigos.add(lastArtigo);
            lastArtigo = null;
        }

        if (qName.compareTo("TRABALHO-EM-EVENTOS") == 0) {
            evento.add(lastEvento);
            lastEvento = null;
        }

        if (qName.compareTo("CAPITULO-DE-LIVRO-PUBLICADO") == 0||
                qName.compareTo("LIVRO-PUBLICADO-OU-ORGANIZADO")==0) {
            capitulo.add(lastCapitulo);
            lastCapitulo = null;
        }
        if (qName.compareTo("PROJETO-DE-PESQUISA") == 0) {
            projeto.add(lastProjeto);
            lastProjeto = null;
        }

        if (qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-MESTRADO") == 0 ||
                qName.compareTo("ORIENTACOES-CONCLUIDAS-PARA-DOUTORADO") ==0 ||
                qName.compareTo("OUTRAS-ORIENTACOES-CONCLUIDAS")==0 ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-MESTRADO") ==0  ||
                qName.compareTo("ORIENTACAO-EM-ANDAMENTO-DE-DOUTORADO")==0)  {
            orientacao.add(lastOrientacao);
            lastOrientacao = null;
        }

        if (qName.compareTo("SOFTWARE")==0) {
            tecnica.add(lastTecnica);
            lastTecnica = null;
        }

        if (qName.compareTo("PATENTE")==0) {
            tecnica.add(lastTecnica);
            lastTecnica = null;
        }
    }

    /**
     * evento characters do SAX. Ã‰ onde podemos recuperar as informaÃ§Ãµes texto
     * contidas no documento XML (textos contidos entre tags). Neste exemplo,
     * recuperamos os nomes dos paÃ­ses, a populaÃ§Ã£o e a moeda
     *
     */
    public void characters(char[] ch, int start, int length)
            throws SAXException {

        String texto = new String(ch, start, length);

        // ------------------------------------------------------------
        // --- TRATAMENTO DAS INFORMAÃ‡Ã•ES DE ACORDO COM A TAG ATUAL ---
        // ------------------------------------------------------------

        if ((tagAtual != null) && (tagAtual.compareTo("DADOS-GERAIS") == 0)) {
            System.out.print(texto + " - nome: " + cur.NOME_COMPLETO);
        }
    }
}
