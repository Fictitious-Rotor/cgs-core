package org.core.splitcgs.lightutils.commons;

import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;
import org.core.splitcgs.lightutils.utils.MultiblockFunctions;
import org.core.splitcgs.lightutils.utils.ReportUtils;

import javax.inject.Inject;
import java.util.Optional;

import static org.core.splitcgs.lightutils.utils.MultiblockFunctions.performRadialBlockOperation;

public abstract class RadialCommons extends PlayerDemander {
    @Inject private LightLocationStoredMH lightLocationMetadata;

    protected void performRadialOperation(final Player runningPlayer,
                                          final String[] arguments,
                                          final MultiblockFunctions.BlockOperation operation,
                                          final ReportUtils.ReportMassOperationFunction reportFunc) {
        final Optional<Integer> noOfConvertedBlocks = performRadialBlockOperation(runningPlayer, arguments, operation, lightLocationMetadata);

        noOfConvertedBlocks.ifPresent(n -> reportFunc.report(runningPlayer, n));
    }
}
