package Extrator.extratorLSG.api.readers;

import Extrator.extratorLSG.api.model.*;

import java.util.HashSet;

import static Extrator.extratorLSG.api.readers.Inicializador.c;

public class Parsing {
    public static void parseOneLattes(String identificador, String arquivo, HashSet<String> setores)  {

        System.out.print("Importando: " + arquivo + " -> ");

        extratorLSG imp = new extratorLSG(identificador);
        imp.fazerParsing(arquivo);

        try {
            imp.cur.persist(c);
        } catch (Exception e) {
            System.out.print("<erro-curriculo>");
        }

        //setores
        if (setores != null) {
            Setor s = null;
            boolean first = true;
            for (String setor : setores) {
                s = new Setor();
                //se houver barra, tira tudo que estÃ¡ depois
                s.nome = setor;
                try {
                    if (first) {
                        s.persist(c, imp.cur, first);
                        first = false;
                    } else {
                        s.persist(c, imp.cur, first);
                    }
                }catch (Exception e) {
                    System.out.print("<erro-setor>");
                }

            }
        }

        for (Formacao a : imp.formacao) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-formacao>");
            }
        }

        for (Artigo a : imp.artigos) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-artigo>");
            }
        }

        for (TrabalhoEvento a : imp.evento) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-trabalho_evento>");
            }
        }

        for (CapituloELivro a : imp.capitulo) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-capitulo>");
            }
        }

        for (Tecnica a : imp.tecnica) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-tecnica>");
            }
        }

        for (Projeto a : imp.projeto) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-projeto>");
            }
        }

        for (Orientacao a : imp.orientacao) {
            try {
                a.persist(c, imp.cur);
            } catch (Exception e) {
                System.out.print("<erro-orientacao>");
            }
        }

        /**
         * *
         */
        System.out.print("Finalizado\n");
    }
}
