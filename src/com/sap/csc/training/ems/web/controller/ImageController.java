package com.sap.csc.training.ems.web.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.apache.chemistry.opencmis.commons.spi.ObjectService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.internal.libraries.asm.tree.TryCatchBlockNode;

import com.sap.csc.training.ems.document.DocumentService;

/**
 * Servlet implementation class ImageController
 */
public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String method;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		method = request.getParameter("method");

		switch (method) {
		case "add":
			String tempPath = "C:\\Dev";
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setRepository(new File(tempPath));
			try {
				List<FileItem> fileItems = upload.parseRequest(request);
				Iterator<FileItem> iterator = fileItems.iterator();
				while (iterator.hasNext()) {
					FileItem item = iterator.next();
					if (item.isFormField()) { // if normal data
						System.out.println("Normal Data" + item.getFieldName());
						System.out.println(item.getString("utf-8"));
					} else { // if file
						// get file's name and content
						String filename = item.getName();
						InputStream is = item.getInputStream();
						DocumentService documentService = new DocumentService();

						String imgId = documentService.createImg(filename, is);
						if (!imgId.equals("Image Already Exist!")) {
							response.addCookie(new Cookie("imgId", imgId));
						} else {
							response.addCookie(new Cookie("imgId", ""));
						}
					}
				}
			} catch (FileUploadException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "show":
			try {
				String imgId = request.getParameter("imgId");
				System.out.println("imgId: " + imgId);
				DocumentService documentService = new DocumentService();
				// String content = documentService.getImg(imgId);

				response.setContentType("image/jpeg");
				IOUtils.copy(documentService.getImg(imgId),
						response.getOutputStream());
				
				// response.getOutputStream().write();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "update":

			break;
		}
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
