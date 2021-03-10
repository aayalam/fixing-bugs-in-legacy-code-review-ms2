package com.assetco.hotspots.optimization;

import com.assetco.hotspots.optimization.*;
import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.HotspotKey.*;
import static org.junit.jupiter.api.Assertions.*;

public class BugsTests {
    @Test
    public void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
      final var partnerVendor = makeVendor(Partner);
      final int maximumShowcaseItems = 5;
        var missing =
          givenAssetInResultsWithVendor(partnerVendor);
        givenAssetInResultsWithVendor(makeVendor(Partner));
        var expected = givenAssetsInResultsWithVendor(maximumShowcaseItems - 1, partnerVendor);

        whenOptimize();

        thenHotspotDoesNotHave(Showcase, missing);
        thenHotspotHasExactly(Showcase, expected);
    }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
      return null;
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
        return null;
    }

    private Asset getAsset(AssetVendor vendor) {
      return null;
    }

    private AssetPurchaseInfo getPurchaseInfo() {
      return null;
    }

    private void thenHotspotHasExactly(HotspotKey hotspotKey, List<Asset> expected) {
    }

    private ArrayList<Asset> givenAssetsInResultsWithVendor(int count, AssetVendor vendor) {
        var result = new ArrayList<Asset>();
        for (var i = 0; i < count; ++i) {
            result.add(givenAssetInResultsWithVendor(vendor));
        }
        return result;
    }

    private void whenOptimize() {
    }

    private void thenHotspotDoesNotHave(HotspotKey key, Asset... forbidden) {
    }
}
