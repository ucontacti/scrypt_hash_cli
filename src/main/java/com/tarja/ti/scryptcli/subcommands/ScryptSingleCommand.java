package com.tarja.ti.scryptcli.subcommands;

import static picocli.CommandLine.*;

import com.tarja.ti.server.shared.util.passphrase.PBKDF2PasswordUtil;


@Command(
  name = "single"
)

public class ScryptSingleCommand implements Runnable 
{
    @Option(names = {"-pwd", "--password"}, required = true, split = ",", description = "Password (can be multiple if splited by ',' character")
    private String[] pwds;

    @Option(names = {"-s", "--salt"}, description = "Optional Salt value, if not entered a Salt will be generated and displayed on execution")
    private String salt;

    @Override
    public void run() 
    {
        if (pwds != null)
        {
            if (salt == null)
            {
                salt = PBKDF2PasswordUtil.getSalt(30);
                System.out.println("Salt: " + salt);    
            }
            for (String pwd : pwds)
            {
                System.out.println("Scrypted Password: " + PBKDF2PasswordUtil.generateSecurePassword(pwd, salt));
            }
        }
    }
}