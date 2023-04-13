package misaka.app.push;

import artoria.data.bean.BeanUtils;
import artoria.data.json.JsonUtils;
import artoria.util.CollectionUtils;
import artoria.util.MapUtils;
import artoria.util.StringUtils;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.DeviceType;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static artoria.common.Constants.FAILURE;
import static artoria.common.Constants.SUCCESS;

public class JPushProvider implements PushProvider {
    private static Logger log = LoggerFactory.getLogger(JPushProvider.class);
    private JPushClient jpushClient;
//    private String appId;

    public JPushProvider(String masterSecret,
                         String appKey,
                         HttpProxy httpProxy,
                         ClientConfig clientConfig) {
        if (clientConfig == null) {
            clientConfig = ClientConfig.getInstance();
        }
        this.jpushClient = new JPushClient(masterSecret, appKey, httpProxy, clientConfig);
    }

    private Notification createNotification(PushMessage pushMessage) {
        // Parameters.
        Map<String, String> extras = pushMessage.getNotificationExtras();
        String content = pushMessage.getNotificationContent();
        String title = pushMessage.getNotificationTitle();
        Collection<String> platforms = pushMessage.getPlatforms();
        if (platforms == null) { platforms = new ArrayList<String>(); }
        Boolean allPlatform = pushMessage.getAllPlatform();
        allPlatform = allPlatform != null && allPlatform;
        if (StringUtils.isBlank(content)) { return null; }
        Notification.Builder notificationBuilder = Notification.newBuilder();

        // Android notification.
        if (allPlatform || platforms.contains("android")) {
            AndroidNotification.Builder builder = AndroidNotification.newBuilder();
            if (StringUtils.isNotBlank(title)) { builder.setTitle(title); }
            if (MapUtils.isNotEmpty(extras)) { builder.addExtras(extras); }
            builder.setAlert(content);
            notificationBuilder.addPlatformNotification(builder.build());
        }

        // IOS notification.
        if (allPlatform || platforms.contains("ios")) {
            IosNotification.Builder builder = IosNotification.newBuilder();
            if (MapUtils.isNotEmpty(extras)) { builder.addExtras(extras); }
            if (StringUtils.isNotBlank(title)) {
                // IOS alert.
                // https://developer.apple.com/library/archive/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/PayloadKeyReference.html
                IosAlert.Builder iosAlertBuilder = IosAlert.newBuilder();
                // subtitle 子标题默认 null
                iosAlertBuilder.setTitleAndBody(title, null, content);
                IosAlert iosAlert = iosAlertBuilder.build();
                builder.setAlert(iosAlert);
            }
            else {
                builder.setAlert(content);
            }
            //builder.incrBadge(1);
            //builder.setSound("sound.caf");
            builder.setContentAvailable(true);
            notificationBuilder.addPlatformNotification(builder.build());
        }

        // Win phone Notification.
        if (allPlatform || platforms.contains("winphone")) {
            WinphoneNotification.Builder builder = WinphoneNotification.newBuilder();
            if (StringUtils.isNotBlank(title)) { builder.setTitle(title); }
            if (MapUtils.isNotEmpty(extras)) { builder.addExtras(extras); }
            builder.setAlert(content);
            notificationBuilder.addPlatformNotification(builder.build());
        }

        return notificationBuilder.build();
    }

    private Audience createAudience(PushMessage pushMessage) {
        Collection<String> registrationIds = pushMessage.getRegistrationIds();
        Collection<String> aliases = pushMessage.getAliases();
        Collection<String> tagsAnd = pushMessage.getTagsAnd();
        Collection<String> tagsOr = pushMessage.getTagsOr();
        Collection<String> tagsNot = pushMessage.getTagsNot();
        Boolean allAudience = pushMessage.getAllAudience();
        // Check.  // todo
        if (allAudience != null && allAudience) { return Audience.all(); }
        Audience.Builder audienceBuilder = Audience.newBuilder();
        if (CollectionUtils.isNotEmpty(aliases)) {
            AudienceTarget target = AudienceTarget.alias(aliases);
            audienceBuilder.addAudienceTarget(target);
        }
        if (CollectionUtils.isNotEmpty(registrationIds)) {
            AudienceTarget target = AudienceTarget.registrationId(registrationIds);
            audienceBuilder.addAudienceTarget(target);
        }
        if (CollectionUtils.isNotEmpty(tagsAnd)) {
            AudienceTarget target = AudienceTarget.tag_and(tagsAnd);
            audienceBuilder.addAudienceTarget(target);
        }
        if (CollectionUtils.isNotEmpty(tagsOr)) {
            AudienceTarget target = AudienceTarget.tag(tagsOr);
            audienceBuilder.addAudienceTarget(target);
        }
        if (CollectionUtils.isNotEmpty(tagsNot)) {
            AudienceTarget target = AudienceTarget.tag_not(tagsNot);
            audienceBuilder.addAudienceTarget(target);
        }
        return audienceBuilder.build();
    }

    private Message createMessage(PushMessage pushMessage) {
        Map<String, String> extras = pushMessage.getMessageExtras();
        String content = pushMessage.getMessageContent();
        String title = pushMessage.getMessageTitle();
        // Message.
        Message.Builder messageBuilder = Message.newBuilder();
        if (StringUtils.isNotBlank(title)) { messageBuilder.setTitle(title); }
        if (MapUtils.isNotEmpty(extras)) { messageBuilder.addExtras(extras); }
        messageBuilder.setMsgContent(content);
        return messageBuilder.build();
    }

    private Options createOptions(PushMessage pushMessage) {
        Boolean production = pushMessage.getProduction();
        // Option.
        Options.Builder optionsBuilder = Options.newBuilder();
        if (production != null) {
            optionsBuilder.setApnsProduction(production);
        }
        //optionsBuilder.setSendno(pushMessage.getId());
        //optionsBuilder.setTimeToLive(timeToLive);
        return optionsBuilder.build();
    }

    private Platform createPlatform(PushMessage pushMessage) {
        Collection<String> platforms = pushMessage.getPlatforms();
        if (platforms == null) { platforms = new ArrayList<String>(); }
        Boolean allPlatform = pushMessage.getAllPlatform();
        if (allPlatform != null && allPlatform) { return Platform.all(); }
        // Platform.
        Platform.Builder platformBuilder = Platform.newBuilder();
        // Android.
        if (platforms.contains("android")) {
            platformBuilder.addDeviceType(DeviceType.Android);
        }
        // IOS.
        if (platforms.contains("ios")) {
            platformBuilder.addDeviceType(DeviceType.IOS);
        }
        // Win phone.
        if (platforms.contains("winphone")) {
            platformBuilder.addDeviceType(DeviceType.WinPhone);
        }
        return platformBuilder.build();
    }

    private PushPayload convert(PushMessage pushMessage) {
        // Parameters.
        String notificationContent = pushMessage.getNotificationContent();
        String messageContent = pushMessage.getMessageContent();
        PushPayload.Builder payloadBuilder = PushPayload.newBuilder();
        Collection<String> platforms = pushMessage.getPlatforms();
        Boolean allPlatform = pushMessage.getAllPlatform();
        // Audience.
        Audience audience = createAudience(pushMessage);
        payloadBuilder.setAudience(audience);
        // Notification.
        if (StringUtils.isNotBlank(notificationContent)) {
            Notification notification = createNotification(pushMessage);
            payloadBuilder.setNotification(notification);
        }
        // Message.
        if (StringUtils.isNotBlank(messageContent)) {
            Message message = createMessage(pushMessage);
            payloadBuilder.setMessage(message);
        }
        // Option.
        Options options = createOptions(pushMessage);
        payloadBuilder.setOptions(options);
        // Platform.
        Platform platform = createPlatform(pushMessage);
        payloadBuilder.setPlatform(platform);
        return payloadBuilder.build();
    }

    @Override
    public PushResult send(PushMessage pushMessage) {
        log.info("JPush send push input: {}", JsonUtils.toJsonString(pushMessage));
        PushResult result = new PushResult();
        try {
            PushPayload pushPayload = convert(pushMessage);
            //pushPayload.resetOptionsTimeToLive(timeToLive);
            cn.jpush.api.push.PushResult pushResult = jpushClient.sendPush(pushPayload);
            result.rawData(pushResult);
            result.setCode(SUCCESS);
            result.putAll(BeanUtils.beanToMap(pushResult));
        }
        catch (APIConnectionException e) {
            log.error("Connection error. ", e);
            result.setCode(FAILURE);
            result.setMessage(e.getMessage());
        }
        catch (APIRequestException e) {
            String errorMessage = e.getErrorMessage();
            int errorCode = e.getErrorCode();
            int status = e.getStatus();
            log.error("Request error. ", e);
            result.setCode(String.valueOf(errorCode));
            result.setMessage(errorMessage);
            result.put("status", status);
        }
        log.info("JPush push result output: {}", JsonUtils.toJsonString(result.toMap()));
        return result;
    }

}
