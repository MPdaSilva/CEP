package br.cep.tela;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

public class CEPBrasil extends JFrame {

	private JPanel contentPane;
	private JTextField txtCEP;
	private JTextField txtEndereco;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CEPBrasil frame = new CEPBrasil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CEPBrasil() {
		setTitle("CEPs");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CEPBrasil.class.getResource("/br/cep/imagens/brasil.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 255);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbCEP = new JLabel("CEP:");
		lbCEP.setBounds(10, 24, 46, 14);
		contentPane.add(lbCEP);
		
		JLabel lbEndereco = new JLabel("Endereço:");
		lbEndereco.setBounds(10, 62, 68, 14);
		contentPane.add(lbEndereco);
		
		JLabel lbBairro = new JLabel("Bairro:");
		lbBairro.setBounds(10, 87, 46, 14);
		contentPane.add(lbBairro);
		
		JLabel lbCidade = new JLabel("Cidade:");
		lbCidade.setBounds(10, 124, 46, 14);
		contentPane.add(lbCidade);
		
		txtCEP = new JTextField();
		txtCEP.setBounds(95, 21, 150, 20);
		contentPane.add(txtCEP);
		txtCEP.setColumns(10);
		
		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(95, 59, 269, 20);
		contentPane.add(txtEndereco);
		
		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(95, 90, 269, 20);
		contentPane.add(txtBairro);
		
		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(95, 121, 269, 20);
		contentPane.add(txtCidade);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
			
		});
		btnLimpar.setBounds(221, 152, 143, 23);
		contentPane.add(btnLimpar);
		
		JButton btnConsultaCEP = new JButton("Consulta CEP");
		btnConsultaCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtCEP.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Informe Cep");
					txtCEP.requestFocus();
				}else {
					busca();
				}
			}
		});
		btnConsultaCEP.setBounds(40, 152, 143, 23);
		contentPane.add(btnConsultaCEP);
		
		JLabel lbUF = new JLabel("UF:");
		lbUF.setBounds(255, 24, 17, 14);
		contentPane.add(lbUF);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO"}));
		comboBox.setBounds(282, 20, 82, 22);
		contentPane.add(comboBox);
		
		RestrictedTextField validador = new RestrictedTextField(txtCEP);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSair.setBounds(40, 186, 324, 23);
		contentPane.add(btnSair);
		validador.setOnlyNums(true);
		validador.setLimit(8);
	}
	private void busca() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCEP.getText();
		
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
		        Element element = it.next();
		        if (element.getQualifiedName().equals("cidade")) {
		        	txtCidade.setText(element.getText());
		        }
		        if (element.getQualifiedName().equals("bairro")) {
		        	txtBairro.setText(element.getText());
		        }
		        if (element.getQualifiedName().equals("uf")) {
		        	comboBox.setSelectedItem(element.getText());
		        }
		        if (element.getQualifiedName().equals("tipo_logradouro")) {
		        	tipoLogradouro = element.getText();
		        }
		        if (element.getQualifiedName().equals("logradouro")) {
		        	logradouro = element.getText();
		        }
		        if (element.getQualifiedName().equals("resultado")) {
		        	resultado = element.getText();
		        	if(resultado.equals("1")) {
		        		
		        	}else {
		        		JOptionPane.showMessageDialog(null, "CEP Inexistente");
		        	}
		        }
		    }
			
			txtEndereco.setText(tipoLogradouro + " - " + logradouro);
		}catch (Exception e){
			
		}
	}

	private void limpar() {

		txtCEP.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		comboBox.setSelectedItem(null);
	}
}















