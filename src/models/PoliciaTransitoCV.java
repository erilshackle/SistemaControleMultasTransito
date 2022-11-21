package models;

import models.licenca.Licenca;
import models.multa.Multa;
import models.condutor.Condutor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe Entidade Singleton Pattern Design Implementado
 *
 * @author eril.carvalho
 */
public class PoliciaTransitoCV {

    private static PoliciaTransitoCV INSTANCE;

    public static final int PONTOS_LIMITE = 36;

    public static final int DIAS_LIMITE = 31;

    public static final int DIAS_JUSTICA = 60;

    private final ArrayList<Condutor> condutoresJustica;

    private final ArrayList<Lei> leis;

    private final ArrayList<Licenca> licencas;

    private final ArrayList<Condutor> condutores;

    private PoliciaTransitoCV() {
        condutores = new ArrayList<>();
        condutoresJustica = new ArrayList<>();
        licencas = new ArrayList<>();
        leis = new ArrayList<>();
    }

    /**
     * Singleton Pattern
     *
     * @return unique PoliciaTransitoCV instance object
     */
    public static PoliciaTransitoCV getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PoliciaTransitoCV();
        }
        return INSTANCE;
    }

    /**
     *
     * @return
     */
    public final ArrayList<Licenca> getLicencas() {
        ArrayList<Licenca> licenses = new ArrayList<>();
        licenses.addAll(licencas);
        return licenses;
    }
    
    public boolean hasCondutor(Condutor condutor){
        return condutores.contains(condutor);
    }
    
    public Condutor getCondutor(long numeroLicenca){
        for(Condutor driver : condutores){
            if(driver.getLicenca().getNumeroLicenca() == numeroLicenca){
                return driver;
            }
        }
        return null;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Condutor> getCondutores() {
    ArrayList<Condutor> drivers = new ArrayList<>();
        drivers.addAll(condutores);
        return drivers;
    }
    
    /**
     *
     * @param driver witch would be added to the list
     * @param license whose driver own and must be unique
     * @return false if license was registered before for an driver true if
     * driver and its license has been added to the list
     */
    public boolean addCondutor(Condutor driver) {
        if (hasLicenca(driver.getLicenca())) {
            return false;
        }
        condutores.add(driver);
        licencas.add(driver.getLicenca());
        return true;
    }

    public boolean eliminarCondutor(Condutor condutor) {
        licencas.remove(condutor.getLicenca());
        return condutores.remove(condutor);
    }

    /**
     *
     * @param artigo
     * @param seccao
     * @return retorna uma lei dado um numero de artigo e seccao
     */
    public Lei getLei(int artigo, int seccao) {
        for (Lei law : leis) {
            if (law.getArtigo() == artigo && law.getSeccao() == seccao) {
                return law;
            }
        }
        return null;
    }

    public ArrayList<Lei> getLeis() {
        return new ArrayList<>(leis);
    }

    public boolean criarLei(Lei law) {
        if (getLei(law.getArtigo(), law.getSeccao()) != null) {
            return false;
        }
        leis.add(law);
        return true;
    }

    public boolean criarLei(int art, int secc, String desc, float vlr, int pts) {
        return criarLei(new Lei(art, secc, desc, vlr, pts));
    }
    
    public boolean modificarLei(Lei lei, String newDescription, float newValor, int newPontos) {
        if (!leis.contains(lei)) {
            return false;
        } else if (newValor <= 0 || newPontos <= 0) {
            return false;
        }
        if(!newDescription.isEmpty())
            lei.setDescricao(newDescription);
        lei.setValorMulta(newValor);
        lei.setPontos(newPontos);
        return true;
    }
    
    public boolean modificarLei(int artigo, int seccao, String newDescription, float newValor, int newPontos) {
        Lei law;
        if ((law = getLei(artigo, seccao)) == null) {
            return false;
        }
        return modificarLei(law, newDescription, newValor, newPontos);
    }
    
    public boolean eliminarLei(Lei lei) {
        return leis.remove(lei);
    }

    public boolean eliminarLei(int artigo, int seccao) {
        return eliminarLei(this.getLei(artigo, seccao));
    }

    public ArrayList<Licenca> pesquisarLicencas(int numero) {
        String license = String.valueOf(numero);
        Pattern pattern = Pattern.compile(license, Pattern.CASE_INSENSITIVE);
        ArrayList<Licenca> licenses = new ArrayList<>();
        licencas.forEach(licenca -> {
            Matcher matcher = pattern.matcher(String.valueOf(
                    licenca.getNumeroLicenca())
            );
            if (matcher.find()) {
                licenses.add(licenca);
            }
        });
        return licenses;
    }
    
    public ArrayList<Lei> pesquisarLeis(long artigo) {
        ArrayList<Lei> laws = new ArrayList<>();
        leis.forEach( lei -> {
            if(lei.getArtigo() == artigo){
                laws.add(lei);
            }
        } );
        return laws;
    }
    
    /**
     *
     * @param nome
     * @return
     */
    public ArrayList<Condutor> pesquisarCondutores(String nome) {
        // Usar RegEx para retornar todos os condutores que contenham *nome*
        Pattern pattern = Pattern.compile(nome, Pattern.CASE_INSENSITIVE);
        ArrayList<Condutor> drivers = new ArrayList<>();
        for (Condutor driver : condutores) {
            Matcher matcher = pattern.matcher(driver.getNome());
            if (matcher.find()) {
                drivers.add(driver);
            }
        }
        return drivers;
    }
    
    public ArrayList<Condutor> pesquisarCondutores(Ilha ilha) {
        ArrayList<Condutor> drivers = new ArrayList<>();
        for (Condutor driver : condutores) {
            if (driver.getIlha() == ilha) {
                drivers.add(driver);
            }
        }
        return drivers;
    }

    public ArrayList<Condutor> pesquisarCondutor(int licenca) {
        String license = String.valueOf(licenca);
        Pattern pattern = Pattern.compile(license, Pattern.CASE_INSENSITIVE);
        ArrayList<Condutor> drivers = new ArrayList<>();
        condutores.forEach(driver -> {
            Matcher matcher = pattern.matcher(String.valueOf(
                    driver.getLicenca().getNumeroLicenca())
            );
            if (matcher.find()) {
                drivers.add(driver);
            }
        });
        return drivers;
    }

    public ArrayList<Condutor> pesquisarCondutores(int idade) {
        ArrayList<Condutor> drivers = new ArrayList<>();
        condutores.forEach( driver -> {
            if(driver.getIdade() == idade){
                drivers.add(driver);
            }
        });
        return drivers;
    }

    public ArrayList<Condutor> getCondutoresJustica() {
        return condutoresJustica;
    }
    
    public boolean levarAJustica(Condutor condutor){
        if(condutoresJustica.contains(condutor) || !hasCondutor(condutor) )
            return false;
        condutoresJustica.add(condutor);
        return true;
    }
    
    public boolean tirarDaJustica(Condutor condutor){
        if(!hasCondutor(condutor) && !condutoresJustica.contains(condutor)){
            return false;
        }
        condutoresJustica.remove(condutor);
        return true;
    }
    // Privates methods
    private boolean hasLicenca(Licenca license) {
        for (Licenca l : this.licencas) {
            if (l.getNumeroLicenca() == license.getNumeroLicenca()) {
                return true;
            }
        }
        return false;
    }
    

}
