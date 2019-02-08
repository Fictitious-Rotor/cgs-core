package org.core.cgs.subplugins.lightutils.commons;

import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.CuboidSelectionSessionMH;
import org.core.cgs.subplugins.lightutils.metadata.LightLocationStoredMH;
import org.core.cgs.subplugins.lightutils.utils.MultiblockFunctions;
import org.core.cgs.subplugins.lightutils.utils.ReportUtils;

import java.util.Optional;

import static org.core.cgs.subplugins.lightutils.utils.MultiblockFunctions.performRangeBlockOperation;

public class RangeCommons {
    protected boolean performRangeOperation(final PrimedPLI PPLI,
                                            final MetadataBundle metadataBundle,
                                            final MultiblockFunctions.BlockOperation operation,
                                            final ReportUtils.ReportMassOperationFunction reportFunc) {
        final LightLocationStoredMH lLMH = metadataBundle.getHandler(LightLocationStoredMH.class);
        final CuboidSelectionSessionMH cSMH = metadataBundle.getHandler(CuboidSelectionSessionMH.class);
        final Optional<Integer> noOfConvertedBlocks = performRangeBlockOperation(PPLI, operation, cSMH, lLMH);

        noOfConvertedBlocks.ifPresent(n -> reportFunc.report(PPLI, n));

        return noOfConvertedBlocks.isPresent() && noOfConvertedBlocks.get() > 0;
    }
}
