package org.synapse.modules.responses.util;

import org.synapse.core.Response;

// Tombstone responses are just placeholders
// They can be used for testing responses
public class TombstoneResponse extends Response
{
    void main()
    {
        logger.info("Tombstone hit");
    }
}
