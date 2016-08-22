package com.rarasnet.rnp.autenticacao.controllers.activities;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rarasnet.rnp.autenticacao.R;
import com.rarasnet.rnp.autenticacao.controllers.network.requests.RegisterRequest;
import com.rarasnet.rnp.autenticacao.controllers.network.tasks.RegisterTask;
import com.rarasnet.rnp.shared.network.responses.GenericResponse;

import java.util.regex.Pattern;


/**
 *  Activity de Registro de Usuário
 *
 */

public class RegisterActivity extends FragmentActivity {


    //Interface de comunicação entre a AsyncTask que faz a requisição do servidor, e
    // a Activity chamada
    public interface RegisterUserCallback {
        public void onRegisterTaskCallback(GenericResponse response);
    }


    private static final String TIPO_PACIENTE = "2";
    private static final String TIPO_MEDICO = "3";
    private static final String TIPO_PESQUISADOR = "4";

    private static final String SEXO_MASCULINO = "1";
    private static final String SEXO_FEMININO = "2";

    private String tipoUsuario = TIPO_PACIENTE;
    private String sexoUsuario = SEXO_MASCULINO;

    private ProgressBar pb_carregando;
    private AppCompatEditText tv_email;
    private AppCompatEditText tv_login;
    private AppCompatEditText tv_senha;
    private AppCompatEditText tv_senhaConf;
    private AppCompatEditText tv_nome;
    private AppCompatEditText tv_sobrenome;


    private TextInputLayout tilEmail;
    private TextInputLayout tilLogin;
    private TextInputLayout tilSenh;
    private TextInputLayout tilConfSenha;
    private TextInputLayout tilNome;
    private TextInputLayout tilSobrenome;


    private RegisterUserCallback registerUserCallback = new RegisterUserCallback() {
        @Override
        public void onRegisterTaskCallback(GenericResponse response) {
            pb_carregando.setVisibility(View.INVISIBLE);

            if(response == null) {
                Toast.makeText(RegisterActivity.this, "Erro de Conexão.",
                        Toast.LENGTH_LONG).show();
                return;
            }else {
                handleWebServiceResponseError(response.getStatus());
            }

            if(response.getStatus().equals("ok")) {
                Toast.makeText(RegisterActivity.this, "Sucesso no Cadastro!", Toast.LENGTH_LONG).show();
                finish();
            }
            hideKeyboard();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();

        if(actionBar != null)
                actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.content);
        configRegisterForm();

        Spinner tipoUsuarioSpinner = (Spinner) findViewById(R.id.act_login_sp_tpusuario);
        ArrayAdapter<CharSequence> tipoUsuarioSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_usuarios, android.R.layout.simple_spinner_item);
        tipoUsuarioSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        //tipoUsuarioSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoUsuarioSpinner.setAdapter(tipoUsuarioSpinnerAdapter);
        tipoUsuarioSpinner.setOnItemSelectedListener(onUserTypeSelectedListener);

        Spinner sexoUsuarioSpinner = (Spinner) findViewById(R.id.act_login_sp_sexo);
        ArrayAdapter<CharSequence> sexoUsuarioAdapter = ArrayAdapter.createFromResource(this,
                R.array.sexo_usuario, android.R.layout.simple_spinner_item);
        sexoUsuarioAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sexoUsuarioSpinner.setAdapter(sexoUsuarioAdapter);
        sexoUsuarioSpinner.setOnItemSelectedListener(onGenderTypeSelectedListener);

        pb_carregando = (ProgressBar) findViewById(R.id.act_register_pb_carregando);
        TextView tv_telaLogin = (TextView) findViewById(R.id.act_register_tv_login);

        tv_telaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setRegisterButtonListener();
    }

    //"Esconde" o teclado após a submissão dos dados para o servidor
    private void hideKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void setRegisterButtonListener() {
        TextView tv_cadastrar = (TextView) findViewById(R.id.act_register_tv_cadastrar);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
                if(!areAllFieldsValid()) {
                    Toast.makeText(RegisterActivity.this, "Ainda existem campos vazios!"
                            , Toast.LENGTH_SHORT).show();
                    validarDatos();
                    return;
                }else if(!passwordMatches()){
                    Toast.makeText(RegisterActivity.this, "As senhas digitadas são diferentes."
                            , Toast.LENGTH_SHORT).show();
                    return;
                }
                validarDatos();

                String login = tv_login.getText().toString();
                String senha = tv_senha.getText().toString();
                String email = tv_email.getText().toString();
                String nome = tv_nome.getText().toString();
                String sobrenome = tv_sobrenome.getText().toString();

                Log.d("tt",nome);

                Log.d("teste reg", login);

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setLogin(login);
                registerRequest.setSenha(senha);
                registerRequest.setEmail(email);
                registerRequest.setNome(nome);
                registerRequest.setSobrenome(sobrenome);

                registerRequest.setTipo("TIPO_PACIENTE");
                registerRequest.setSexo("SEXO_MASCULINO");

                pb_carregando.setVisibility(View.VISIBLE);
                new RegisterTask(registerUserCallback).execute(registerRequest);
            }
        };
        tv_cadastrar.setOnClickListener(listener);
    }


    private void configRegisterForm() {
        tv_login = (AppCompatEditText) findViewById(R.id.act_register_et_login);
        tv_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        tv_senha = (AppCompatEditText) findViewById(R.id.act_register_et_senha);
        tv_senhaConf = (AppCompatEditText) findViewById(R.id.act_register_et_senhaConf);
        tv_email = (AppCompatEditText) findViewById(R.id.act_register_et_email);
        tv_nome = (AppCompatEditText) findViewById(R.id.act_login_et_nome);
        tv_sobrenome = (AppCompatEditText) findViewById(R.id.act_login_et_sobrenome);

        tilEmail = (TextInputLayout) findViewById(R.id.act_register_ti_email);
        tilLogin = (TextInputLayout) findViewById(R.id.act_register_ti_login);
        tilSenh = (TextInputLayout) findViewById(R.id.act_register_ti_senha);
        tilConfSenha = (TextInputLayout) findViewById(R.id.act_register_ti_senhaConf);
        tilNome = (TextInputLayout) findViewById(R.id.act_login_ti_nome);
        tilSobrenome = (TextInputLayout) findViewById(R.id.act_login_ti_sobrenome);


        tv_nome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNome.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_sobrenome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSobrenome.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                esCorreoValido(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSenh.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_senhaConf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilConfSenha.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilLogin.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    private boolean isNomeValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            tilNome.setError("Nome inválido");
            return false;
        } else {
            tv_nome.setError(null);
        }

        return true;
    }

    private boolean isSobrenomeValido(String sobrenome) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(sobrenome).matches() || sobrenome.length() > 30) {
            tilSobrenome.setError("Sobrenome inválido");
            return false;
        } else {
            tv_sobrenome.setError(null);
        }

        return true;
    }

    private boolean isSenhaValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            tilSenh.setError("Senha inválida");
            return false;
        } else {
            tv_senha.setError(null);
        }

        return true;
    }

    private boolean isConfSenhaValido(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            tilConfSenha.setError("Confirmação de senha inválida");
            return false;
        } else {
            tv_senhaConf.setError(null);
        }

        return true;
    }


    private boolean isLoginValido(String login) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(login).matches() || login.length() > 30) {
            tilLogin.setError("Login  inválida");
            return false;
        } else {
            tv_login.setError(null);
        }

        return true;
    }



    private boolean esCorreoValido(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilEmail.setError("Email inválido");
            return false;
        } else {
            tilEmail.setError(null);
        }

        return true;
    }

    private void validarDatos() {
        String nome = tilNome.getEditText().getText().toString();
        String sobrenome = tilSobrenome.getEditText().getText().toString();
        String senha = tilConfSenha.getEditText().getText().toString();
        String confSenha = tilSenh.getEditText().getText().toString();

        String login = tilLogin.getEditText().getText().toString();
        String correo = tilEmail.getEditText().getText().toString();

        boolean a = isNomeValido(nome);
        boolean b = isSobrenomeValido(sobrenome);
        boolean c = isSenhaValido(senha);
        boolean d = isConfSenhaValido(confSenha);
        boolean e = isLoginValido(login);
        boolean f = esCorreoValido(correo);

        if (a && b && c && d && e && f) {

           Log.d("#Result","Cadastrado com sucesso");
        }

    }

    private void handleWebServiceResponseError(String errorData) {

        switch (errorData) {
            case "er_login":
                Toast.makeText(RegisterActivity.this, "Este Login já está Cadastrado!"
                        , Toast.LENGTH_SHORT).show();
                break;

            case "er_email":
                Toast.makeText(RegisterActivity.this, "Este e-mail já está Cadastrado!"
                        , Toast.LENGTH_SHORT).show();
                break;

            case "er":
                Toast.makeText(RegisterActivity.this, "Erro no servidor"
                        , Toast.LENGTH_SHORT).show();
        }

    }

    private boolean areAllFieldsValid() {
        if(tv_email.getText().toString().isEmpty()) {
            return false;
        }

        if(tv_login.getText().toString().isEmpty()) {
            return false;
        }

        if(tv_senha.getText().toString().isEmpty()) {
            return false;
        }

        if(tv_senhaConf.getText().toString().isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean passwordMatches() {
        String senha = tv_senha.getText().toString();
        String senhaConf = tv_senhaConf.getText().toString();

        if(senha.equals(senhaConf)) {
            return true;
        }
        return false;
    }

    private AdapterView.OnItemSelectedListener onUserTypeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            CharSequence sequence = (CharSequence) parent.getItemAtPosition(pos);
            String userType = sequence.toString();
            if(userType.equals("Paciente")) {
                tipoUsuario = TIPO_PACIENTE;
            } else if (userType.equals("Medico")) {
                tipoUsuario = TIPO_MEDICO;
            } else if (userType.equals("Pesquisador")) {
                tipoUsuario = TIPO_PESQUISADOR;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    private AdapterView.OnItemSelectedListener onGenderTypeSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            CharSequence sequence = (CharSequence) parent.getItemAtPosition(pos);
            String userType = sequence.toString();
            if(userType.equals("Masculino")) {
                sexoUsuario = SEXO_MASCULINO;
            } else if (userType.equals("Feminino")) {
                sexoUsuario = SEXO_FEMININO;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };
}
