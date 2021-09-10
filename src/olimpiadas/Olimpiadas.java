package olimpiadas;

import java.util.List;
import java.util.*;
import java.io.*;

class Atleta {
	private String nome;
	private String modalidade;
	private int medalha_ouro;
        private int medalha_prata;
        private int medalha_bronze;
        private int medalhas_total;
	private String origem;
	private float tempo_obtido;
        
	
	public String getNome() {
            return this.nome;
	}
	
	public String getModalidade() {
            return this.modalidade;
	}
	
	public int getMedalhaOuro() {
            return this.medalha_ouro;
	}
        
        public int getMedalhaPrata() {
            return this.medalha_prata;
	}
        
        public int getMedalhaBronze() {
            return this.medalha_bronze;
	}
        
        public int getTotal() {
            return this.medalha_ouro + this.medalha_prata + this.medalha_bronze;
        }
	
	public String getOrigem() {
            return this.origem;		
	}
	
	public float getTempo_obtido () {
            return this.tempo_obtido;
	}
	
	public void setNome(String nome) {
            this.nome = nome;
	}
	
	public void setModalidade(String modalidade) {
            this.modalidade = modalidade;
	}
	
	public void incrementaOuro(int quantidade) {
            this.medalha_ouro = this.medalha_ouro + quantidade;
	}
        
        public void incrementaPrata(int quantidade) {
            this.medalha_prata = this.medalha_prata + quantidade;
	}
        
        public void incrementaBronze(int quantidade) {
            this.medalha_bronze = this.medalha_bronze + quantidade;
	}
	
	public void setOrigem(String origem) {
            this.origem = origem;
	}
	
	public void setTempo_obtido(float tempo_obtido) {
            String array_modalidade[] = this.modalidade.split(" ");
            if (array_modalidade[0].equals("Revezamento")) {
                this.tempo_obtido = tempo_obtido/4;
            } else {
                this.tempo_obtido = tempo_obtido;
            }
	}
}

class Paises {
	private String pais;
	private int ouro;
	private int prata;
	private int bronze;
	private int medalhas_total;
	
	public void setPais(String pais) {
            this.pais = pais;
	}
	
	public void incrementaOuro(int medalhas_ouro) {
            this.ouro = this.ouro + medalhas_ouro;
	}
	
	public void incrementaPrata(int medalhas_prata) {
            this.prata = this.prata + medalhas_prata;
	}
	
	public void incrementaBronze(int medalhas_bronze) {
            this.bronze = this.bronze + medalhas_bronze;
	}
        
        public void defineTotal() {
            this.medalhas_total = this.ouro + this.prata + this.bronze;
        }

	public String getPais() {
            return this.pais;
	}
	
	public int getOuro() {
            return this.ouro;
	}
	
	public int getPrata() {
            return this.prata;
	}
	
	public int getBronze() {
            return this.bronze;
	}
        
        public int getTotal() {
            return this.medalhas_total;
        }
}


class Olimpiadas {
    public static void main(String[] args) {
        List<Atleta> atletas = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Entre com o nome do arquivo [ex.: dados.txt]: ");
        String nome_arquivo = sc.nextLine();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(nome_arquivo).getAbsolutePath()));
            String string_arquivo = "";
            while (br.ready()) {
                String linha = br.readLine();
                string_arquivo = string_arquivo + linha;
            }
            String array_dados[] = string_arquivo.split(";");
            Atleta novo_atleta = new Atleta();
            int i = 0;
            int j = 0;
            int k = 0;
            for (i = 0; i < array_dados.length; i++) {
                j = i;
		if (j == 0) {
                    novo_atleta = new Atleta();
                    novo_atleta.setModalidade(array_dados[i]);
		} else if (j == 1) {
                    novo_atleta.setNome(array_dados[i]);
                    if (k == 0) {
                        novo_atleta.incrementaOuro(1);
                    } else if (k == 1) {
                        novo_atleta.incrementaPrata(1);
                    } else if (k == 2) {
                        novo_atleta.incrementaBronze(1);
                    }
		} else if (j == 2) {
                    novo_atleta.setOrigem(array_dados[i]);
		} else if (j == 3) {
                    novo_atleta.setTempo_obtido(Float.parseFloat(array_dados[i]));
                    atletas.add(novo_atleta);
                    j = 0;
                    k++;
		}
		if (k == 2) {
                    k = 0;	
		}
            }
            //Adiciona os países em uma lista de classes Paises, sem repetições:
            List<Paises> lista_paises = new ArrayList<>();
            for (i = 0; i < atletas.size(); i++) {
                boolean pais_adicionado = false;
                for (j = 0; j < lista_paises.size(); j++) {
                    if (atletas.get(i).getOrigem().equals(lista_paises.get(j).getPais())) {
                        pais_adicionado = true;
                    }
                }
                if (!pais_adicionado) {
                    Paises novo_pais = new Paises();
                    novo_pais.setPais(atletas.get(i).getOrigem());
                    lista_paises.add(novo_pais);
                }
            }
            //Definição do número de medalhas de outro, prata, broze e medalhas totais de um país:
            for (i = 0; i < lista_paises.size(); i++) {
                for (j = 0; j < atletas.size(); j++) {
                    if (lista_paises.get(i).getPais().equals(atletas.get(j).getOrigem())) {
                        lista_paises.get(i).incrementaOuro(atletas.get(j).getMedalhaOuro());
                        lista_paises.get(i).incrementaPrata(atletas.get(j).getMedalhaPrata());
                        lista_paises.get(i).incrementaBronze(atletas.get(j).getMedalhaBronze());
                        lista_paises.get(i).defineTotal();
                    }
                }
                
            }
            String pais_com_mais_ouro = "";
            for (i = 0; i < lista_paises.size(); i++) {
                boolean nao_mais_ouro = false;
                for (j = 0; j < lista_paises.size(); j++) {
                    if (lista_paises.get(j).getOuro() > lista_paises.get(i).getOuro()) {
                        nao_mais_ouro = true;
                    }
                }
                if (!nao_mais_ouro) {
                    pais_com_mais_ouro = lista_paises.get(i).getPais();
                }
            }
            String pais_com_mais_medalhas = "";
            for (i = 0; i < lista_paises.size(); i++) {
                boolean nao_mais_medalhas = false;
                for (j = 0; j < lista_paises.size(); j++) {
                    if (lista_paises.get(j).getTotal() > lista_paises.get(i).getTotal()) {
                        nao_mais_medalhas = true;
                    }
                }
                if (!nao_mais_medalhas) {
                    pais_com_mais_medalhas = lista_paises.get(i).getPais();
                }
            }
            //Adiciona os atletas em uma lista de classes Atleta, sem repetições:
            List<Atleta> atletas_sem_repeticoes = new ArrayList<>();
            for (i = 0; i < atletas.size(); i++) {
                boolean atleta_adicionado = false;
                for (j = 0; j < atletas_sem_repeticoes.size(); j++) {
                    if (atletas.get(i).getNome().equals(atletas_sem_repeticoes.get(j).getNome())) {
                        atleta_adicionado = true;
                    }
                }
                if (!atleta_adicionado) {
                    Atleta atleta = new Atleta();
                    atleta.setNome(atletas.get(i).getNome());
                    atletas_sem_repeticoes.add(atleta);
                }
            }
            for (i = 0; i < atletas_sem_repeticoes.size(); i++) {
                for (j = 0; j < atletas.size(); j++) {
                    if (atletas_sem_repeticoes.get(i).getNome().equals(atletas.get(j).getNome())) {
                        atletas_sem_repeticoes.get(i).incrementaOuro(atletas.get(j).getMedalhaOuro());
                        atletas_sem_repeticoes.get(i).incrementaPrata(atletas.get(j).getMedalhaPrata());
                        atletas_sem_repeticoes.get(i).incrementaPrata(atletas.get(j).getMedalhaBronze());
                        atletas_sem_repeticoes.get(i).getTotal();
                    }
                }
                
            }
            String atleta_com_mais_ouro = "";
            for (i = 0; i < atletas_sem_repeticoes.size(); i++) {
                boolean nao_mais_ouro = false;
                for (j = 0; j < atletas_sem_repeticoes.size(); j++) {
                    if (atletas_sem_repeticoes.get(j).getMedalhaOuro() > atletas_sem_repeticoes.get(i).getMedalhaOuro()) {
                        nao_mais_ouro = true;
                    }
                }
                if (!nao_mais_ouro) {
                    atleta_com_mais_ouro = atletas_sem_repeticoes.get(i).getNome();
                }
            }
            String atleta_com_mais_medalhas = "";
            for (i = 0; i < atletas_sem_repeticoes.size(); i++) {
                boolean nao_mais_medalhas = false;
                for (j = 0; j < atletas_sem_repeticoes.size(); j++) {
                    if (atletas_sem_repeticoes.get(j).getTotal() > atletas_sem_repeticoes.get(i).getTotal()) {
                        nao_mais_medalhas = true;
                    }
                }
                if (!nao_mais_medalhas) {
                    atleta_com_mais_medalhas = atletas_sem_repeticoes.get(i).getNome();
                }
            }
            String atleta_mais_tempo = "";
            for (i = 0; i < atletas_sem_repeticoes.size(); i++) {
                boolean nao_mais_tempo = false;
                for (j = 0; j < atletas_sem_repeticoes.size(); j++) {
                    if (atletas_sem_repeticoes.get(j).getTempo_obtido() > atletas_sem_repeticoes.get(i).getTempo_obtido()) {
                        nao_mais_tempo = true;
                    }
                }
                if (!nao_mais_tempo) {
                    atleta_mais_tempo = atletas_sem_repeticoes.get(i).getNome();
                }
            }
            System.out.println("País com mais medalhas de ouro: " + pais_com_mais_ouro);
            System.out.println("País com mais medalhas: " + pais_com_mais_medalhas);
            System.out.println("Alteta com mais medalhas de ouro: " + atleta_com_mais_ouro);
            System.out.println("Alteta com mais medalhas: " + atleta_com_mais_medalhas);
            System.out.println("Atleta que gastou mais tempo: " + atleta_mais_tempo);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}