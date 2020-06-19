package com.github.starter.core.secrets;

import com.github.skhatri.mounted.MountedSecretsResolver;
import com.github.skhatri.mounted.model.SecretValue;
import com.github.starter.core.exception.ConfigurationException;

import java.util.Arrays;
import java.util.Optional;

public class SecretsClient {

    private final MountedSecretsResolver mountedSecretsResolver;

    public SecretsClient(MountedSecretsResolver mountedSecretsResolver) {
        this.mountedSecretsResolver = mountedSecretsResolver;
    }

    public final char[] resolve(String key) {
        SecretValue secretValue = this.mountedSecretsResolver.resolve(key);
        Optional<char[]> value = secretValue.getValue();
        if (value.isPresent() && secretValue.hasValue()) {
            char[] secret = value.get();
            return Arrays.copyOf(secret, secret.length);
        }
        throw new ConfigurationException(String.format("secret %s could not be resolved.", key));
    }

}
