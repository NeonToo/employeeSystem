sap.ui.define([ 'jquery.sap.global', 'sap/ui/core/Fragment',
		'sap/ui/core/mvc/Controller', 'sap/ui/model/json/JSONModel',
		"sap/m/MessageToast" ], function(jQuery, Fragment, Controller,
		JSONModel, MessageToast) {
	"use strict";

	var PageController = Controller.extend("ems.view.detail", {
		
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
//			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			
			var url = location.href;
			var id = url.substr(url.lastIndexOf("/") + 1);
			

//			 model.loadData("data/person.json");
			model.loadData("EmployeeController?id=" + id + "&method=find");
			this.getView().setModel(model);
			// this.getView().bindElement("/");
			this._showFormFragment("display");
		},

		handleEditPress : function() {
			this._oInfo = jQuery
					.extend({}, this.getView().getModel().getData());
			this._toggleButtonsAndView(true);
		},

		handleCancelPress : function() {

			// Restore the data
			var oModel = this.getView().getModel();
			var oData = oModel.getData();

			oData = this._oInfo;

			oModel.setData(oData);
			this._toggleButtonsAndView(false);

		},

		handleSavePress : function() {
			this._submitInfo("edit", "Modify");
			this._toggleButtonsAndView(false);
		},

		handleDeletePress : function() {
			if(window.confirm("Are you sure to delete this employee?")){
				this._submitInfo("delete", "Delete");
				window.location.href = "index.html";
			}
		},

		handleUploadComplete : function(oEvent) {
			MessageToast.show("Image Upload Success!");
		},

		handleUploadPress : function(oEvent) {
			var oFileUploader = this.getView().byId("fileUploader");
			oFileUploader.upload();
		},

		_formFragments : {},

		_submitInfo : function(op, msg) {
			var o = JSON.stringify(this.getView().getModel().oData);
			// console.log(o);
			var url = location.href;
			var id = url.substr(url.lastIndexOf("/") + 1);

			$.ajax({
				url : "EmployeeController?id=" + id + "&method=" + op,
				type : "POST",
				dataType : 'json',
				data : {
					oInfo : o
				},
				success : function() {
					MessageToast.show(msg + " Success!");
				}
			});

		},

		_toggleButtonsAndView : function(bEdit) {
			var oView = this.getView();

			// Show the appropriate action buttons
			oView.byId("edit").setVisible(!bEdit);
			oView.byId("delete").setVisible(!bEdit);
			oView.byId("save").setVisible(bEdit);
			oView.byId("cancel").setVisible(bEdit);

			// Set the right form type
			this._showFormFragment(bEdit ? "change" : "display");
		},

		_getFormFragment : function(sFragmentName) {
			var oFormFragment = this._formFragments[sFragmentName];

			if (oFormFragment) {
				return oFormFragment;
			}

			oFormFragment = sap.ui.xmlfragment(this.getView().getId(), "ems.view."
					+ sFragmentName);

			return this._formFragments[sFragmentName] = oFormFragment;
		},

		_showFormFragment : function(sFragmentName) {
			var oPage = this.getView().byId("detailPage");

			oPage.removeAllContent();
			oPage.insertContent(this._getFormFragment(sFragmentName));
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

	return PageController;

});