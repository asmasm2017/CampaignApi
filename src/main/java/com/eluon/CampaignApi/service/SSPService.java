package com.eluon.CampaignApi.service;

import com.eluon.CampaignApi.entity.SSPClaimResult;
import com.eluon.CampaignApi.entity.SSPInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Service
public class SSPService {
    private static final Logger LOG = LoggerFactory.getLogger(SSPService.class);

    @Value("ssp.claimUrl")
    private final String claimUrl = "http://192.168.0.199:4081/claim/";

    @Value("ssp.quotaUrl")
    private final String quotaUrl = "http://192.168.0.199:4081/PULLHandler/GetSubsInfoWS_PS/";

    private final String quotaBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:get=\"http://www.example.org/GetSubsInfo/\"><soapenv:Header/><soapenv:Body><get:GetSubsInfoRequest><Eid>OFFD</Eid><Tid><<DATETIME>></Tid><Msisdn><<MSISDN>></Msisdn><IMSI></IMSI><Lang></Lang></get:GetSubsInfoRequest></soapenv:Body></soapenv:Envelope>";

    public SSPClaimResult claim(String msisdn, int amount){
        SSPClaimResult result = new SSPClaimResult();
        String httpUrl = claimUrl;
        String claimResult = "No";
        LOG.debug ("claim URL: " + httpUrl);

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(9000);
            connection.setReadTimeout(9000);

            connection.setRequestMethod("POST");

            StringBuffer tmpBuf = new StringBuffer();

            if (connection.getResponseCode() !=200) {
                LOG.debug("get response code:" + connection.getResponseCode());

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String inputLine;



                    while ((inputLine = in.readLine()) != null) {
                        tmpBuf.append(inputLine);
                    }
                }
                result.message = tmpBuf.toString();
                result.result = false;
                LOG.error("Error :" + result.result + "," + result.message);
                return result;
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    tmpBuf.append(inputLine);
                }
            }

            String tmpResultString = tmpBuf.toString();

            Document doc = parse_to_doc(tmpResultString);

            NodeList nodes = doc.getElementsByTagName("*");
            for (int index = 0; index < nodes.getLength(); ++index) {
                Element el = (Element) nodes.item(index);
                if (el.getNodeName().equalsIgnoreCase("ClaimResult")) {
                    claimResult = el.getTextContent();
                }

            }

        } catch (MalformedURLException ex) {
            LOG.error("MalformedURLException " ,ex);
            result.result = false;
            result.message = "MalformedURLException " + ex.getMessage();
            return result;
        } catch (ProtocolException ex) {
            LOG.error("ProtocolException " , ex);
            result.result = false;
            result.message = "ProtocolException " + ex.getMessage();
            return result;
        }   catch (SAXException e) {
            LOG.error("SAXException " , e);
            result.result = false;
            result.message = "SAXException " + e.getMessage();
            return result;
        } catch (ParserConfigurationException e) {
            LOG.error("ParserConfigurationException " ,e);
            result.result = false;
            result.message = "ParserConfigurationException " + e.getMessage();
            return result;
        } catch (IOException ex) {
            LOG.error("IOException " , ex);
            result.result = false;
            result.message = "IOException " + ex.getMessage();
            return result;
        }
        LOG.debug("result = " + result);
        LOG.debug("claimResult =" + claimResult);

        result.result = claimResult.equalsIgnoreCase("OK");
        result.message = "OK";

        return result;
    }

    private Document parse_to_doc(String req) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(req) );
        return builder.parse(is);
    }

    public SSPInfoResult getInfo(String msisdn) {
        String httpUrl = quotaUrl;
        SSPInfoResult infoResult = new SSPInfoResult();
        LOG.debug("Check Quota URl: " + httpUrl);

        String httpBody = quotaBody.replaceAll("<<MSISDN>>", msisdn)
                .replaceAll("<<DATETIME>>", msisdn + getCurrentTimeString());
        LOG.debug("Check Quota Body: " + httpBody);
        String packageName = null;
        String expired = null;
        String remainingQuota = null;
        String remainingQuotaO = null;
        String quotaUnit = null;
        String quotaUnitO = null;
        boolean isPostPaid = false;
        String balance = null;
        String expiredBalance = null;
        String paymentDueDate = null;
        String tagihanAsset = null;
        try{

            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(httpBody.getBytes().length));
            connection.setRequestProperty("SOAPAction", "\"http://www.example.org/GetSubsInfo/GetQuota\"");

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(httpBody);
            wr.flush();
            wr.close();

            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLEventReader reader = xif.createXMLEventReader(connection.getInputStream());

            String sServiceType = null;
            String sPackageName = null;
            String sEndDate = null;
            String sName = null;
            String sRemainingQuota = null;
            String sQuotaUnit = null;
            String sDescription = null;
            String sBenefitType = null;
            String sPaymentDueDate = null;
            String sTagihanAsset = null;

            while (reader.hasNext())
            {

                XMLEvent e = reader.nextEvent();
                if (e.isStartElement())
                {
                    StartElement s = e.asStartElement();
                    String elementName = s.getName().getLocalPart();
                    if ("ServiceType".equalsIgnoreCase(elementName)) {
                        sServiceType = reader.getElementText();
                    } else if ("PackageName".equalsIgnoreCase(elementName)) {
                        sPackageName = reader.getElementText();
                    } else if ("EndDate".equalsIgnoreCase(elementName)) {
                        sEndDate = reader.getElementText();
                    } else if ("Name".equalsIgnoreCase(elementName)) {
                        sName = reader.getElementText();
                    } else if ("Description".equalsIgnoreCase(elementName)) {
                        sDescription = reader.getElementText();
                    } else if ("RemainingQuota".equalsIgnoreCase(elementName)) {
                        sRemainingQuota = reader.getElementText();
                    } else if ("QuotaUnit".equalsIgnoreCase(elementName)) {
                        sQuotaUnit = reader.getElementText();
                    } else if ("CustBillInfo".equalsIgnoreCase(elementName)) {
                        isPostPaid = true;
                    } else if ("Payment_Due_Date".equalsIgnoreCase(elementName)) {
                        sPaymentDueDate = reader.getElementText();
                    } else if ("Tagihan_Asset".equalsIgnoreCase(elementName)) {
                        sTagihanAsset = reader.getElementText();
                    } else if ("CustBalanceInfo".equals(elementName)) {
                        balance = reader.getElementText();
                    } else if ("ExpiredDate".equals(elementName)) {
                        expiredBalance = reader.getElementText();
                    } else if ("BenefitType".equals(elementName)) {
                        sBenefitType = reader.getElementText();
                    }
                }
                if (e.isEndElement())
                {
                    EndElement s = e.asEndElement();
                    String elementName = s.getName().getLocalPart();
                    if ("Service".equalsIgnoreCase(elementName))
                    {
                        if (("Add On".equalsIgnoreCase(sServiceType)) &&
                                (sBenefitType != null) && (sBenefitType.contains("DATA")))
                        {
                            packageName = sPackageName;
                            expired = sEndDate;
                        }
                        sServiceType = null;
                        sPackageName = null;
                        sEndDate = null;
                    }
                    else if ("Quota".equalsIgnoreCase(elementName))
                    {
                        if (((sName != null) && (sName.contains("Utama"))) ||
                                ((sDescription != null) && (sDescription.contains("Utama"))))
                        {
                            if ((remainingQuota == null) && (quotaUnit == null) && ("Add On".equalsIgnoreCase(sServiceType)))
                            {
                                remainingQuota = sRemainingQuota;
                                quotaUnit = sQuotaUnit;
                            }
                        }
                        else if (((sName != null) && (sName.contains("Kuota Data Opers"))) ||
                                ((sDescription != null) && (sDescription.contains("Kuota Data Opers"))))
                        {
                            packageName = sName;
                            expired = sEndDate;
                            remainingQuotaO = sRemainingQuota;
                            quotaUnitO = sQuotaUnit;
                        }
                        else if (((sName != null) && (sName.contains("Penggunaan Data saat ini"))) ||
                                ((sDescription != null) && (sDescription.contains("Penggunaan Data saat ini"))))
                        {
                            remainingQuota = sRemainingQuota;
                            quotaUnit = sQuotaUnit;
                        }
                        sName = null;
                        sRemainingQuota = null;
                        sQuotaUnit = null;
                    }
                    else if (("CustBillInfo".equalsIgnoreCase(elementName)) && (isPostPaid))
                    {
                        isPostPaid = true;
                        tagihanAsset = sTagihanAsset;
                        paymentDueDate = sPaymentDueDate;
                    }
                }
            }

        }catch(SocketTimeoutException ste){
            infoResult.result = false;
            infoResult.message = "SocketTimeoutException " + ste.getMessage();
            LOG.error("SocketTimeoutException",ste);
        } catch (MalformedURLException | XMLStreamException mue) {
            infoResult.result = false;
            infoResult.message = "Error " + mue.getMessage();
            LOG.error("Error",mue);
        } catch (IOException ioe) {
            infoResult.result = false;
            infoResult.message = "IOException " + ioe.getMessage();
            LOG.error("IOException",ioe);
        }
        String sQuota = remainingQuota != null ? remainingQuota : remainingQuotaO;
        String sUnit = quotaUnit != null ? quotaUnit : quotaUnitO;

        if ((sQuota != null) && (sUnit != null))
        {
            double r = Double.parseDouble(sQuota);
            if("KB".equalsIgnoreCase(sUnit) && r > 1024){
                r = r/1024;
                sUnit = "MB";
            }
            if("MB".equalsIgnoreCase(sUnit) && r > 1024){
                r = r/1024;
                sUnit = "GB";
            }
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            DecimalFormat df = (DecimalFormat)nf;
            df.applyPattern(".#");
            sQuota = df.format(r);
        }
        LOG.debug("Package Name: " + packageName +
                ", Package Expired: " + (expired != null ? parseExpiredDateToUnix(expired) : 0L) +
                ", Remaining Quota: " + (sQuota == null ? 0 : sQuota) +
                ", Quota Unit: " + sUnit +
                ", isPostPaid: " + isPostPaid +
                ", Balance: " + (balance != null ? balance : 0) +
                ", Balance Expired: " + ((expiredBalance != null) && (!isPostPaid) ? parseExpiredDateToUnix(expiredBalance) : 0) +
                ", Payment Due Date: " + (paymentDueDate != null ? parseExpiredDateToUnix(paymentDueDate) : 0) +
                ", Tagihan Aset: " + (tagihanAsset != null ? tagihanAsset : 0));

        if ((expiredBalance != null) && (!"".equals(expiredBalance))){
            infoResult.result = true;
            infoResult.message ="OK";

            infoResult.content.put("packageName", packageName);
            infoResult.content.put("packageExpired", expired != null ? parseExpiredDateToUnix(expired) : 0);
            infoResult.content.put("packageRemainingQuota", sQuota == null ? 0 : sQuota);
            infoResult.content.put("packageQuotaUnit", sUnit);
            infoResult.content.put("isPostPaid", isPostPaid);
            if (isPostPaid)
            {
                infoResult.content.put("paymentDueDate", parseExpiredDateToUnix(paymentDueDate));
                infoResult.content.put("tagihanAsset", tagihanAsset);
            }
            else if (!isPostPaid)
            {
                infoResult.content.put("balance", (balance != null) && (!balance.isEmpty()) ? Long.parseLong(balance) : 0);
                infoResult.content.put("balanceExpired", (expiredBalance != null) && (!expiredBalance.isEmpty()) ? parseExpiredDateToUnix(expiredBalance) : 0);
            }

        }
        return infoResult;
    }

    private String getCurrentTimeString() {
        String DATE_FORMAT = "yyyyMMddHHmmssSSS";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date());
    }

    private long parseExpiredDateToUnix(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            return formatter.parse(dateInString).getTime();
        } catch (ParseException e) {
            LOG.error("Error ParseException ",e);
        }
        return 0;
    }

}
