fun printStartTotals[ctx, start] {
	ctx.nextRow[];
	ctx.createBundleCell[4, "accountant_gen.leftover", start];
	printStartRow[ctx, start, null, 9, 11];
	
	for[Currency curr : Currency.all[]] {
		ctx.nextRow[];
		printStartRow[ctx, start, curr, 8, 12];
	}
	
	ctx.nextRow[];
}

fun printStartTotals[ctx, start] {
	nextRow[ctx];
	createBundleCell[ctx, 4, "accountant_gen.leftover", start];
	printStartRow[ctx, start, null, 9, 11];
	
	for[Currency curr : Currency.all[]] {
		nextRow[ctx];
		printStartRow[ctx, start, curr, 8, 12];
	}
	
	nextRow[ctx];
}
