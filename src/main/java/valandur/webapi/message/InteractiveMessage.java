package valandur.webapi.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import valandur.webapi.api.cache.CachedObject;
import valandur.webapi.api.message.IInteractiveMessage;
import valandur.webapi.api.message.IInteractiveMessageOption;
import valandur.webapi.api.serialize.JsonDetails;
import valandur.webapi.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InteractiveMessage extends CachedObject<IInteractiveMessage> implements IInteractiveMessage {

    private UUID uuid;
    @Override
    public UUID getUUID() {
        return uuid;
    }

    private String id;
    @Override
    public String getId() {
        return id;
    }

    private UUID target;
    @Override
    @JsonDetails
    public UUID getTarget() {
        return target;
    }

    private List<UUID> targets;
    @Override
    @JsonDetails
    public List<UUID> getTargets() { return targets; }

    private String message;
    @Override
    @JsonIgnore
    public Text getMessage() {
        return TextSerializers.FORMATTING_CODE.deserializeUnchecked(message);
    }
    @JsonProperty("message")
    @JsonDetails
    public String getRawMessage() {
        return message;
    }

    private Boolean once;
    @Override
    @JsonDetails
    public Boolean isOnce() {
        return once;
    }

    private List<InteractiveMessageOption> options;
    @Override
    @JsonDetails
    public List<IInteractiveMessageOption> getOptions() {
        return new ArrayList<>(options);
    }

    @Override
    @JsonIgnore
    public boolean hasOptions() {
        return options != null;
    }




    public InteractiveMessage() {
        super(null);

        this.uuid = UUID.randomUUID();
    }

    @Override
    public String getLink() {
        return Constants.BASE_PATH + "/message/" + uuid;
    }

    @Override
    public Optional<IInteractiveMessage> getLive() {
        return super.getLive();
    }
}