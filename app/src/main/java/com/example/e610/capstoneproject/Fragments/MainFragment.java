package com.example.e610.capstoneproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e610.capstoneproject.Activitys.LoginActivity;
import com.example.e610.capstoneproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E610 on 8/6/2017.
 */
public class MainFragment extends Fragment implements TabLayout.OnTabSelectedListener
        , NavigationView.OnNavigationItemSelectedListener {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    View view;

    String userInfo;
    String userEmail;
    Bundle bundle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_main, container, false);

        bundle=new Bundle();
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.setSupportActionBar(toolbar);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        userInfo=user.getUid();
        userEmail=user.getEmail();
        bundle.putString(getActivity().getString(R.string.user_info),userInfo);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };


        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);

       /*this line to add header view to navigationView programmatically
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_navigation_drawer);*/

        //this line to get existed  headerView in navigationView
        View headerView = navigationView.getHeaderView(0); // 0-index header
        TextView textView=(TextView)headerView.findViewById(R.id.nav_header_textView);
        textView.setText(userEmail);
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager, 0);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);


        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anime) {
            // Handle the camera action
            //Toast.makeText(MainActivity.this," ^__^ " + id,Toast.LENGTH_SHORT).show();
            displaySelectedScreen(id);

        } else if (id == R.id.nav_manga) {
            //Toast.makeText(MainActivity.this," ^__^ " + id,Toast.LENGTH_SHORT).show();
            displaySelectedScreen(id);
        } else if (id == R.id.nav_sing_out) {
            //Toast.makeText(MainActivity.this," ^__^ " + id,Toast.LENGTH_SHORT).show();
            signOut();
        } else if (id == R.id.fav_ani) {
            //Toast.makeText(MainActivity.this," ^__^ " + id,Toast.LENGTH_SHORT).show();
            displaySelectedScreen(id);
            //  reStartLoader.restartLoader();
//            String s=getFavouriteMovies(id);
            //  Toast.makeText(MainActivity.this, s ,Toast.LENGTH_SHORT).show();

        } else if (id == R.id.fav_man) {
            // Toast.makeText(MainActivity.this," ^__^ " + id,Toast.LENGTH_SHORT).show();
            // String s=getFavouriteMovies(id);
            // Toast.makeText(MainActivity.this, s ,Toast.LENGTH_SHORT).show();
            displaySelectedScreen(id);

        }


        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_anime:
                setupViewPager(viewPager, 0);
                break;
            case R.id.nav_manga:
                setupViewPager(viewPager, 1);
                break;
            case R.id.fav_ani:
                setupViewPager(viewPager, 2);
                break;
            case R.id.fav_man:
                setupViewPager(viewPager, 3);
                break;

        }

       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
    }


    private void setupViewPager(ViewPager viewPager, int i) {
        if (i == 0) {

            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

            //Most Popular Anime
            AnimeMainFragment animeMainFragment = new AnimeMainFragment();
            animeMainFragment.setArguments(bundle);
            animeMainFragment.setUrl(getString(R.string.most_popular_anime_link));

            //Top Upcoming Anime
            AnimeMainFragment animeMainFragment1 = new AnimeMainFragment();
            animeMainFragment1.setArguments(bundle);
            animeMainFragment1.setUrl(getString(R.string.top_upcoming_anime_link));

            //Top Airing Anime
            AnimeMainFragment animeMainFragment2 = new AnimeMainFragment();
            animeMainFragment2.setArguments(bundle);
            animeMainFragment2.setUrl(getString(R.string.top_airing_anime_link));

            //Highest Rated Anime
            AnimeMainFragment animeMainFragment3 = new AnimeMainFragment();
            animeMainFragment3.setArguments(bundle);
            animeMainFragment3.setUrl(getString(R.string.highest_rated_anime_link));


            adapter.addFragment(animeMainFragment, getString(R.string.most_popular));
            adapter.addFragment(animeMainFragment1, getString(R.string.top_upcoming));
            adapter.addFragment(animeMainFragment2, getString(R.string.top_airing));
            adapter.addFragment(animeMainFragment3, getString(R.string.highest_rated));
            viewPager.setAdapter(adapter);
        } else if (i == 1) {

            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

            //Most Popular Manga
            MangaMainFragment mangaMainFragment = new MangaMainFragment();
            mangaMainFragment.setArguments(bundle);
            mangaMainFragment.setUrl(getString(R.string.most_popular_manga_link));

            //Highest Rated Manga
            MangaMainFragment mangaMainFragment1 = new MangaMainFragment();
            mangaMainFragment1.setArguments(bundle);
            mangaMainFragment1.setUrl(getString(R.string.highest_rated_manga_link));

            //Top Upcoming Manga
            MangaMainFragment mangaMainFragment2 = new MangaMainFragment();
            mangaMainFragment2.setArguments(bundle);
            mangaMainFragment2.setUrl(getString(R.string.top_upcoming_manga_link));

            //Top Publishing Manga
            MangaMainFragment mangaMainFragment3 = new MangaMainFragment();
            mangaMainFragment3.setArguments(bundle);
            mangaMainFragment3.setUrl(getString(R.string.top_publishing_manga_link));

            adapter.addFragment(mangaMainFragment, getString(R.string.most_popular));
            adapter.addFragment(mangaMainFragment1, getString(R.string.highest_rated));
            adapter.addFragment(mangaMainFragment2, getString(R.string.top_upcoming));
            adapter.addFragment(mangaMainFragment3, getString(R.string.top_publishing));
            viewPager.setAdapter(adapter);
        } else if (i == 2) {

            //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

            AnimeFavouriteFragment animeFavouriteFragment = new AnimeFavouriteFragment();
            AnimeFavouriteFragment animeFavouriteFragment1 = new AnimeFavouriteFragment();
            AnimeFavouriteFragment animeFavouriteFragment2 = new AnimeFavouriteFragment();
            animeFavouriteFragment.setTarget(getString(R.string.favourite));
            animeFavouriteFragment1.setTarget(getString(R.string.completed));
            animeFavouriteFragment2.setTarget(getString(R.string.startWatch));
            animeFavouriteFragment.setArguments(bundle);
            animeFavouriteFragment1.setArguments(bundle);
            animeFavouriteFragment2.setArguments(bundle);

            adapter.addFragment(animeFavouriteFragment, getString(R.string.favourite));
            adapter.addFragment(animeFavouriteFragment1, getString(R.string.completed));
            adapter.addFragment(animeFavouriteFragment2, getString(R.string.startWatch));
            viewPager.setAdapter(adapter);
            //viewPager.setOffscreenPageLimit(2);
        } else if (i == 3) {

            //ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

            MangaFavouriteFragment mangaFavouriteFragment = new MangaFavouriteFragment();
            MangaFavouriteFragment mangaFavouriteFragment1 = new MangaFavouriteFragment();
            MangaFavouriteFragment mangaFavouriteFragment2 = new MangaFavouriteFragment();
            mangaFavouriteFragment.setTarget(getString(R.string.favourite));
            mangaFavouriteFragment1.setTarget(getString(R.string.completed));
            mangaFavouriteFragment2.setTarget(getString(R.string.startWatch));
            mangaFavouriteFragment.setArguments(bundle);
            mangaFavouriteFragment1.setArguments(bundle);
            mangaFavouriteFragment2.setArguments(bundle);


            adapter.addFragment(mangaFavouriteFragment, getString(R.string.favourite));
            adapter.addFragment(mangaFavouriteFragment1, getString(R.string.completed));
            adapter.addFragment(mangaFavouriteFragment2, getString(R.string.startWatch));
            viewPager.setAdapter(adapter);
            //viewPager.setOffscreenPageLimit(2);
        }
    }


    /*
    class ViewPagerAdapter extends FragmentPagerAdapter {
        this line cause logic error "display fragments in view pager when i reset it in wrong way"
        */

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //sign out method
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}
