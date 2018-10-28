//package me.iblitzkriegi.vixio.expressions.message;
//
//import ch.njol.skript.expressions.base.SimplePropertyExpression;
//import ch.njol.skript.util.Date;
//import me.iblitzkriegi.vixio.Vixio;
//import me.iblitzkriegi.vixio.util.UpdatingMessage;
//import me.iblitzkriegi.vixio.util.Util;
//import net.dv8tion.jda.core.entities.Message;
//
//public class ExprDate extends SimplePropertyExpression<UpdatingMessage, Date> {
//    static {
//        Vixio.getInstance().registerPropertyExpression(ExprDate.class, Date.class, "date", "messages")
//                .setName("Date of Message")
//                .setDesc("Get the time a message was sent.")
//                .setExample("reply with \"%date of event-message%\"");
//    }
//
//    @Override
//    protected String getPropertyName() {
//        return "date";
//    }
//
//    @Override
//    public Date convert(UpdatingMessage m) {
//        return Util.getDate(m.getMessage().getCreationTime());
//    }
//
//    @Override
//    public Class<? extends Date> getReturnType() {
//        return Date.class;
//    }
//}
