package org.core.splitcgs.lightutils.commons;

import org.bukkit.entity.Player;
import org.core.splitcgs.lightutils.metadata.session.CuboidSelectionSessionMH;
import org.core.splitcgs.lightutils.metadata.stored.LightLocationStoredMH;
import org.core.splitcgs.lightutils.utils.MultiblockFunctions;
import org.core.splitcgs.lightutils.utils.ReportUtils;

import javax.inject.Inject;
import java.util.Optional;

import static org.core.splitcgs.lightutils.utils.MultiblockFunctions.performRangeBlockOperation;

public abstract class RangeCommons extends PlayerDemander {
    @Inject private LightLocationStoredMH lightLocationHandler;
    @Inject private CuboidSelectionSessionMH cuboidSelectionHandler;

    protected void performRangeOperation(final Player runningPlayer,
                                         final MultiblockFunctions.BlockOperation operation,
                                         final ReportUtils.ReportMassOperationFunction reportFunc) {
        final Optional<Integer> noOfConvertedBlocks = performRangeBlockOperation(runningPlayer, operation, cuboidSelectionHandler, lightLocationHandler);

        noOfConvertedBlocks.ifPresent(n -> reportFunc.report(runningPlayer, n));
    }
}
