using System;

namespace AtRaCon_Client.Core.Config
{
    public class Config
    {
        public string Key { get; private set; }
        public string SValue { get; set; }
        public double DValue { get; set; }
        public int IValue { get; set; }
        public bool BValue { get; set; }
        public ValueType VType { get; private set; }

        public Config(string key, string value)
        {
            Key = key;
            SValue = value;
            VType = ValueType.STRING;
        }

        public Config(string key, int value)
        {
            Key = key;
            IValue = value;
            VType = ValueType.INT;
        }

        public Config(string key, double value)
        {
            Key = key;
            DValue = value;
            VType = ValueType.DOUBLE;
        }

        public Config(string key, bool value)
        {
            Key = key;
            BValue = value;
            VType = ValueType.BOOL;
        }
    }

    public class ValueType
    {
        public static readonly ValueType INT = new ValueType();
        public static readonly ValueType DOUBLE = new ValueType();
        public static readonly ValueType STRING = new ValueType();
        public static readonly ValueType BOOL = new ValueType();
    }
}