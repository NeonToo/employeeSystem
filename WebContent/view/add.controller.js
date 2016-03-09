sap.ui.define([ 'jquery.sap.global', 'sap/ui/core/mvc/Controller',
		'sap/ui/model/json/JSONModel', "sap/m/MessageToast" ], function(jQuery,
		Controller, JSONModel, MessageToast) {
	"use strict";

	var AddController = Controller.extend("view.add", {

		/**
		 * Called when a controller is instantiated and its View controls (if
		 * available) are already created. Can be used to modify the View before
		 * it is displayed, to bind event handlers and do other one-time
		 * initialization.
		 * 
		 * @memberOf dataform.Page
		 */
		onInit : function() {
			var model = new sap.ui.model.json.JSONModel();

			// model.loadData("data/person.json");
			this.getView().setModel(model);
			// this.getView().bindElement("/");
		},

		handleCancelPress : function() {

			// Restore the data
			var oModel = this.getView().getModel();
			var oData = oModel.getData();

			oData = this._oInfo;
			oModel.setData(oData);
		},

		handleSavePress : function() {
			this._submitInfo();
		},

		handleUploadComplete : function(oEvent) {
			MessageToast.show("Image Upload Success!");
		},

		handleUploadPress : function(oEvent) {
			var oFileUploader = this.getView().byId("fileUploader");
			oFileUploader.upload();
		},

		_formFragments : {},

		_submitInfo : function() {
			var o = this.getView().getModel().oData;
			var imgId = $.cookie("imgId");
			console.log("imgId: " + imgId);

			if (imgId != "") {
				o["imgId"] = imgId;
				console.log(o);

				$.ajax({
					url : "EmployeeController?method=add",
					type : "POST",
					dataType : 'json',
					data : {
						oInfo : JSON.stringify(o)
					},
					success : function() {
						MessageToast.show("Add Success!");
					}
				});
				window.location.href = "index.html";
			} else {
				MessageToast.show("Image Already Exist!");
			}
		},

	/**
	 * Similar to onAfterRendering, but this hook is invoked before the
	 * controller's View is re-rendered (NOT before the first rendering!
	 * onInit() is used for that one!).
	 * 
	 * @memberOf dataform.Page
	 */
	// onBeforeRendering: function() {
	//
	// },
	/**
	 * Called when the View has been rendered (so its HTML is part of the
	 * document). Post-rendering manipulations of the HTML could be done here.
	 * This hook is the same one that SAPUI5 controls get after being rendered.
	 * 
	 * @memberOf dataform.Page
	 */
	// onAfterRendering: function() {
	//
	// },
	/**
	 * Called when the Controller is destroyed. Use this one to free resources
	 * and finalize activities.
	 * 
	 * @memberOf dataform.Page
	 */
	// onExit: function() {
	//
	// }
	});

	return AddController;

});