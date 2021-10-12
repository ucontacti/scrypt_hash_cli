package com.tarja.ti.scryptcli.subcommands;

import static picocli.CommandLine.*;

import com.tarja.ti.server.shared.util.passphrase.PBKDF2PasswordUtil;


@Command(
  name = "single"
)

public class ScryptSingleCommand implements Runnable 
{
    @Option(names = {"-pwd", "--password"}, required = true, split = ",")
    private String[] pwds;

    @Option(names = {"-s", "--salt"})
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