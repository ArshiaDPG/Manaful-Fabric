package org.digitalpear.manaful.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.common.ManaSource;

public class ManaAttachments {

    public static final AttachmentType<ManaSource> MANA = AttachmentRegistry.create(Manaful.id("mana_source"), doubleBuilder ->
            doubleBuilder.initializer(() -> ManaSource.INSTANCE)
                    .syncWith(ManaSource.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
                    .copyOnDeath()
            );

    public static void init() {}

}
