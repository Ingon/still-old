fun printHeaders[ctx, name, start, end] {
	ctx.nextRow[];
	ctx.createStringCell[4, name];
	
	ctx.nextRow[];
	ctx.createBundleCell[4, "accountant_gen.period", start, end];
	
	ctx.nextRow[];
	
	ctx.nextRow[];
	createSingleCell[ctx, 0, "code", 5.76];
	createSingleCell[ctx, 1, "type", 3.71];
	createSingleCell[ctx, 2, "invoice", 13.57];
	createSingleCell[ctx, 3, "date", 10.00];
	createSingleCell[ctx, 4, "description", 8.43];
	
	ctx.setColumnWidth[5, 8.43];
	ctx.setColumnWidth[6, 8.43];
	ctx.setColumnWidth[7, 8.43];

	createSingleCell[ctx, 8, "expence", 12];
	createSingleCell[ctx, 9, "expence", 12];
	createSingleCell[ctx, 10, "expence", 12];
	createSingleCell[ctx, 11, "revenue", 12];
	createSingleCell[ctx, 12, "revenue", 12];
	
	ctx.nextRow[];
	ctx.createBundleCell[8, "accountant_gen.currency"];
	ctx.createBundleCell[9, "accountant_gen.leva"];
	ctx.createBundleCell[10, "accountant_gen.dds"];
	ctx.createBundleCell[11, "accountant_gen.leva"];
	ctx.createBundleCell[12, "accountant_gen.currency"];

	ctx.nextRow[];
}
