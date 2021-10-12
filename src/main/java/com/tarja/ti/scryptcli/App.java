package com.tarja.ti.scryptcli;

import com.tarja.ti.scryptcli.subcommands.ScryptSingleCommand;
import com.tarja.ti.scryptcli.subcommands.ScryptMultiCommand;
import picocli.CommandLine;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.RunLast;

@Command(
    name = "scrypt-gen",
    description = "Generate Scrypted passwrod"
)

public class App implements Runnable 
{
    public static void main( String[] args )
    {
        CommandLine commandLine = new CommandLine(new App());
        commandLine.addSubcommand("single", new ScryptSingleCommand());
        commandLine.addSubcommand("multi", new ScryptMultiCommand());

        commandLine.parseWithHandler(new RunLast(), args);
    }

    @Override
    public void run() 
    {
        System.out.println("Scrypt hash CLI.");
    }
}

