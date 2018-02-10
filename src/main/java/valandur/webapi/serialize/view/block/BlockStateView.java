package valandur.webapi.serialize.view.block;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.trait.BlockTrait;
import org.spongepowered.api.data.manipulator.DataManipulator;
import valandur.webapi.WebAPI;
import valandur.webapi.api.cache.misc.CachedCatalogType;
import valandur.webapi.api.serialize.BaseView;
import valandur.webapi.api.serialize.JsonDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApiModel("BlockState")
public class BlockStateView extends BaseView<BlockState> {

    private BlockType type;
    @ApiModelProperty(value = "The type of block this block state is from", access = "string")
    public CachedCatalogType<BlockType> getType() {
        return new CachedCatalogType<>(type);
    }

    public void setType(String type) { }


    public BlockStateView(BlockState value) {
        super(value);

        this.type = value.getType();
    }

    @JsonDetails
    @ApiModelProperty("Additional data attached to the block state")
    public Map<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();
        // Add traits
        for (Map.Entry<BlockTrait<?>, ?> entry : value.getTraitMap().entrySet()) {
            data.put(entry.getKey().getName(), entry.getValue());
        }
        // Add data
        Map<String, Class<? extends DataManipulator<?, ?>>> supData = WebAPI.getSerializeService().getSupportedData();
        for (Map.Entry<String, Class<? extends DataManipulator<?, ?>>> entry : supData.entrySet()) {
            try {
                Optional<?> m = value.getManipulators().stream()
                        .filter(i -> i.asMutable().getClass().equals(entry.getValue()))
                        .findFirst();

                if (!m.isPresent())
                    continue;

                data.put(entry.getKey(), ((DataManipulator) m.get()).copy());
            } catch (IllegalArgumentException | IllegalStateException ignored) {
            }
        }
        return data;
    }
}
