package censusanalyser;

public class CvsLoaderFactory {

    public enum Country {
        INDIA {
            public CensusAdaptor create() {
                return new IndiaCensusAdaptor();
            }
        },
        US {
            public CensusAdaptor create() {
                return new USCensusAdaptor();
            }
        };

        public CensusAdaptor create() {
            return null;
        }
    }

    public static  CensusAdaptor createAdaptor(Country validatorType) {
        return validatorType.create();
    }
}
