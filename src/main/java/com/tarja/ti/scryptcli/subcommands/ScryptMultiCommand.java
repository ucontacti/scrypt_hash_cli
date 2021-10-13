package com.tarja.ti.scryptcli.subcommands;

import static picocli.CommandLine.*;

import com.tarja.ti.server.shared.util.passphrase.PBKDF2PasswordUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Command(
  name = "multi"
)

public class ScryptMultiCommand implements Runnable 
{
    @Option(names = {"-pi", "--password_input"}, required = true, description = "Password file input path (passwords should be separated by newline)")
    private String pwd_path;

    @Option(names = {"-s", "--salt"}, description = "Optional Salt value, if not entered a Salt will be generated and displayed on execution")
    private String salt;

    @Option(names = {"-si", "--salt_input"}, description = "Salt file input path (passwords should be separated by newline) and should be length of password entries")
    private String salt_path;

    @Option(names = {"-o", "--output"}, description = "Optional output file path to scrypted passwords")
    private String output_path;

    @Override
    public void run()
    {
        try {
            if (pwd_path != null)
            {
                String[] pwds = readLines(pwd_path);
                String[] output =   new String[pwds.length];
                if (salt_path != null)
                {
                    String[] salts = readLines(salt_path);
                    if (pwds.length != salts.length)
                    {    
                        System.out.println("Number of items in password file and salt file missmatch.");
                    }
                    else
                    {
                        if (output_path == null)
                        {
                            for (int i = 0; i < pwds.length; i++) 
                            {
                                System.out.println("Scrypted Password: " + PBKDF2PasswordUtil.generateSecurePassword(pwds[i], salts[i]));
                            }
                        }
                        else
                        {
                            for (int i = 0; i < pwds.length; i++) 
                            {
                                output[i] = PBKDF2PasswordUtil.generateSecurePassword(pwds[i], salts[i]);
                            }
                            writeLines(output_path, output);
                        }
                    }
                }
                else
                {
                    if (salt == null)
                    {
                        salt = PBKDF2PasswordUtil.getSalt(30);
                        System.out.println("Salt: " + salt);    
                    }
                    if (output_path == null)
                    {
                        for (int i = 0; i < pwds.length; i++) 
                        {
                            System.out.println("Scrypted Password: " + PBKDF2PasswordUtil.generateSecurePassword(pwds[i], salt));
                        }

                    }
                    else
                    {
                        for (int i = 0; i < pwds.length; i++) 
                        {
                            output[i] = PBKDF2PasswordUtil.generateSecurePassword(pwds[i], salt);
                        }
                        writeLines(output_path, output);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    public String[] readLines(String filename) throws IOException 
    {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public void writeLines(String filename, String[] output) throws IOException
    {
        FileWriter fileWriter = new FileWriter(filename);
        int len = output.length;
        for (int i = 0; i < len; i++) 
        {
           fileWriter.write(output[i] + "\n");
        }
        fileWriter.close();     
    }
}