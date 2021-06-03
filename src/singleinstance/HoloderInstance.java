package singleinstance;

public final class HoloderInstance {
    private byte[] data = new byte[1024];

    private HoloderInstance() {
    }

    private static class Holder {
        private static HoloderInstance holoderInstance = new HoloderInstance();
    }

    public HoloderInstance getHolderInstance() {
        return Holder.holoderInstance;
    }
}
