package org.core.cgs.subplugins.lightutils.commons;

import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PrimedPLI;
import org.core.cgs.subplugins.lightutils.metadata.stored.LightLocationStoredMH;
import org.core.cgs.subplugins.lightutils.utils.MultiblockFunctions;
import org.core.cgs.subplugins.lightutils.utils.ReportUtils;

import java.util.Optional;

import static org.core.cgs.subplugins.lightutils.utils.MultiblockFunctions.performRadialBlockOperation;

public class RadialCommons {
    protected boolean performRadialOperation(final PrimedPLI PPLI,
                                             final String[] arguments,
                                             final MetadataBundle metadataBundle,
                                             final MultiblockFunctions.BlockOperation operation,
                                             final ReportUtils.ReportMassOperationFunction reportFunc) {

        final LightLocationStoredMH mH = metadataBundle.getHandler(LightLocationStoredMH.class);
        final Optional<Integer> noOfConvertedBlocks = performRadialBlockOperation(PPLI, arguments, operation, mH);

        noOfConvertedBlocks.ifPresent(n -> reportFunc.report(PPLI, n));

        return noOfConvertedBlocks.isPresent() && noOfConvertedBlocks.get() > 0;
    }
}
