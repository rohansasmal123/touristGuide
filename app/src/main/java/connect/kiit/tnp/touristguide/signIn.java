package connect.kiit.tnp.touristguide;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mail,pwd;
    private ProgressDialog dialog;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();

        btn = (Button)findViewById(R.id.signin);
        mail = (EditText)findViewById(R.id.email);
        pwd = (EditText)findViewById(R.id.password);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(signIn.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                String email =  mail.getText().toString();
                String password =  pwd.getText().toString();
                if(!password.isEmpty()&&!email.isEmpty()){
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(signIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("sm", "signInWithEmail:success");
                                        dialog.dismiss();
                                        startActivity(new Intent(signIn.this,HomeActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        dialog.dismiss();
                                        Log.w("sm", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                }
                else {
                    new AlertDialog.Builder(signIn.this)
                            .setTitle("SportMaze")
                            .setMessage("Please provide email and password.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .show();
                }
            }
        });
    }
}
