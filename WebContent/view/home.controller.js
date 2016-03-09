sap.ui.define([ 'jquery.sap.global', 'sap/ui/core/mvc/Controller',
		'sap/ui/model/json/JSONModel', 'sap/ui/core/UIComponent' ],
		function(jQuery, Controller, JSONModel, UIComponent) {
			"use strict";

			var TableController = Controller.extend("ems.view.home", {

				onInit : function() {
					// set explored app's demo model on this sample
					var model = new sap.ui.model.json.JSONModel();

					model.loadData("EmployeeController?method=getAll");
					this.getView().setModel(model);
				},

				onPress : function(oEvent) {
					 this._showDetail(oEvent.getSource());
				},

				_showDetail : function(oItem) {
//					console.log(oItem.getBindingContext().getProperty("id"));
					UIComponent.getRouterFor(this).navTo("detail", {
						id: oItem.getBindingContext().getProperty("id")
					});
				}
			});

			return TableController;

		});