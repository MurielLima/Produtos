package view;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Produto;

public class PrincipalController implements Initializable {

    private List<Produto> produtos = new ArrayList<>();
    private double quantidade = 0;
    private final NumberFormat nf
            = NumberFormat.getInstance(Locale.getDefault());
    private Produto P;
    @FXML
    private TextField txtFldQtd;
    @FXML
    private Label lblNome;
    @FXML
    private Label lblUn;
    @FXML
    private Label lblCor;
    @FXML
    private Label lblPreco;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblPrecoTotal;
    @FXML
    private ComboBox cmbProdutos;

    @FXML
    private void btnFecharClick(Event event) {
        System.exit(0);
    }

    @FXML
    private void btnLimparClick(Event event) {
        cmbProdutos.getSelectionModel().clearSelection();
        lblNome.setText("");
        lblUn.setText("");
        lblCor.setText("");
        lblPreco.setText("");
        lblTotal.setText("");
        txtFldQtd.setText("");
        P = null;
    }

    private String precoTotal() {
        double preco;
        try {
            preco = (double) nf.parse(lblPreco.getText()).doubleValue();
        } catch (ParseException ex) {
            preco = 0;
        }
        return nf.format(quantidade * preco);
    }
    private final ChangeListener<? super String> listenerProduto
            = (observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*?")
                && !newValue.isEmpty()) {
                    txtFldQtd.setText(oldValue);
                } else {
                    if (P != null) {
                        try {
                            quantidade = nf.parse(txtFldQtd.getText()).doubleValue();
                            lblTotal.setText(precoTotal());
                        } catch (ParseException ex) {
                            txtFldQtd.setText("");
                        }
                    }
                }
            };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        produtos.add(new Produto("Kit Teclado E Mouse Wireless Dell Km636", 137.40, "Preto", "Kit"));
        produtos.add(new Produto("Mouse Wireless Dell Wm126 ", 60.38, "Azul", "Unidade"));
        produtos.add(new Produto("Fone Headset Gamer Hyperx", 236.10, "Azul", "Unidade"));
        produtos.add(new Produto("Kit Gamer Proc. I5+Mb H110m-cs", 1699, "Preto", "Kit"));
        produtos.add(new Produto("Kit Processador Fx 6300+Placa Mãe Asrock ", 691.10, "Azul", "Kit"));
        produtos.add(new Produto("Processador Am3+ Fx-6300", 334.10, "Azul", "Unidade"));
        produtos.add(new Produto("Monitor Led Tn 18,5", 1399.80, "Preto", "Unidade"));
        produtos.add(new Produto("Monitor Alienware 24.5 Aw2518hf", 1899, "Preto", "Unidade"));
        produtos.add(new Produto("Fonte Corsair 450w", 229, "Azul", "Unidade"));
        cmbProdutos.setItems(FXCollections.observableList(produtos));

        cmbProdutos.valueProperty().addListener(
                new ChangeListener<Produto>() {

            @Override
            public void changed(ObservableValue<? extends Produto> observable, Produto oldValue, Produto newValue) {
                //Seta tudo sobre o produto que vai aparecer na interface
                P = (Produto) cmbProdutos.getSelectionModel().getSelectedItem();
                if (P != null) {
                    lblNome.setText(newValue.getNome());
                    lblUn.setText(newValue.getUnidade());
                    lblCor.setText(newValue.getCor());
                    lblPreco.setText(nf.format(newValue.getPreco()));
                    try {
                        quantidade = nf.parse(txtFldQtd.getText()).doubleValue();
                        lblTotal.setText(precoTotal());
                    } catch (ParseException ex) {
                        System.out.println("Erro ao selecionar quantidade");
                    }
                }
            }
        }
        );
        txtFldQtd.textProperty().addListener(listenerProduto);

    }
}
