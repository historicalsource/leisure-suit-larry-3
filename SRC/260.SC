;**
;**	Logics for room 260 -- On the Beach
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(script#				260)

(public
	rm260				0
)


(define	vL&TdoingItNaked			258)
(define	vLdeadSunburn				259)
(define	vLizard						260)
	(define	lLizRisingEast		2)
	(define	lLizRisingWest		3)
	(define	lLizTongueEast		4)
	(define	lLizTongueWest		5)

(define	vLwithTowel					261)
		(define	LarryTowelX	184)
		(define	LarryTowelY	170)
	(define	lSpreadingTowel	0)
	(define	lLarryTowel			1)
	(define	lLlyingDown			2)

(define	vTawni						262)
	(define	lTawniTowel			0)
	(define	lWiggleAss			1)
	(define	lStandingUp			2)
	(define	lTyingTop			3)
	(define	lTurningHeadR		4)
	(define	lTurningHeadL		5)

(define	vL&TdoingIt					263)
	(define	elTturningLeft		0)
	(define	elJumpingL			1)
	(define	elTrisingOnL		2)
	(define	lHumping				3)
	(define	elDismounting		4)
	(define	elLrising			5)
	(define	lLscratching		6)
	(define	elLintoPants		7)
	(define	lCrabShaking		8)
	(define	elPullingUpPants	9)

(define	vVendorKnife				264)
;**	Tawni's face closeup is		265
;**	Tawni's body closeup is		266
(define	vVendorCarvings			267)
(define	vVendorShells				268)
(define	vVendorBeads				269)
;	(define	lVselling			2)
;	(define	lLselling		 	4)

;(define	vLstooping					709)
;(define	vLundressing				701)	;**	261)
;(define	vLundressingHunk			721)	;**	705)


;(define	sForeplay					008)	
;(define	sInterrupted				009)	
;(define	sSurf							260)
;(define	sKeystone					261)


(define	NONE							-1)
(define	nearTowel 				(& (ego onControl) cLtRed))
(define	onTowel	 				(& (ego onControl) cYellow))

(define	TawniX						153)
(define	TawniY						165)

(define	TawniTowelX 				154)
(define	TawniTowelY					169)


(enum												;**	RoomScript state values
	intro
	demo1
	demo2
	demo3
	demo4
	drowning
	deathOccurredSuddenly
	deathDelay
	UBdying
	gotoTtowel
	bendOverTtowel
	takeTtowel
	TtowelDone
	gotoLtowel
	spreadTtowel
	getOnTowel
	undressLarry
	lieDown
	onLtowel
	startRoasting
	roasting
	cooked
	wellDone
	getUp
	getDressed
	gotoTheTowel
	bendOverLtowel
	gotLtowel
	LtowelDone
	getLaid
	TjumpsL
	TraisesUp
	humping
	knifeManReady
	bargain
	thatWasRude
	sheffieldSteel
	comeInAcase
	twentyBucksBitch
	givesMoney
	TgoesDownAgain
	youContemplate
	screwSomeMore
	larryGetsCrabs
	larryRises
	theyAreUp
	terrible
	CRABS
	somethingInHere
	raiseYourPants
	RweThru
	roNesponse
	restartTheSurfSound
)


(enum												;**	LizardScript state values
	LizIntro
	bringLizIn
	pauseB4standing
	standLizUp
	LizStanding
	LizLicking
	makeLizGoDown
	byeByeLiz
	emergencyExit
	hereComesLizardDick
	LizHalfOut
	lickDick
)


(enum												;**	VendorScript state values
	vendorInit
	bringVendorIn
	souvenirs
	souvenirs2
	getYour
	wannaBuyA
	buyThisOne
	maybeBuyIt
	wannaBuyThis
	itsAsale
	makeVendorLeave
	vendorGone
)


(enum												;**	TawniScript state values
	Tintro
	wiggleAss
	wiggleAss2
	turnHead
	turnHead2
	getTawniUp
	pullUpTop
	goto266
	takeOffTop
	TliesDown
	TbuttFucks
	TinLimbo
)


(enum												;**	beachState values
	T_NOT_YET_HERE
	T_ON_TOWEL
	LOOKING_AT_TAWNI
	SCREWING
	T_PISSED
	SELLING_SCULPTURE
	TOWEL_ON_BEACH
	BEACH_DONE
)


(local
	lieDownAfterTakingTowel 
	seenMsg
	tawniBusy
	rollCount
	jumpCount
	[string  40] 
	[string2 22]
)


(synonyms
	(vendor man )
	(tawni girl )
)


(instance rm260 of Room
	(properties
		picture		260
		horizon		66
		west			250
	)

	(method (init &tmp i)
		(super init)

		(self
			setScript 		RoomScript,
		)

		;**	Comment in/out the following block for debugging purposes:

;**			(= i (GetNumber {BeachState (from 0-9)}))
;**			(if (and i
;**						(!= i ERROR))
;**				(= beachState i)
;**				(if (== beachState T_ON_TOWEL)
;**					(= curVendor vVendorCarvings)
;**				)
;**			)
;**
;**			(if egoIsPatti
;**				(= beachState BEACH_DONE)
;**			)

		;**	end debugging block

		(if (and (TstFlg	beenIn350)
					(== beachState T_NOT_YET_HERE))
			(= beachState T_ON_TOWEL)
			(= curVendor vVendorCarvings)
		)

		(if (and	(ego has iWood)
					( < beachState TOWEL_ON_BEACH)
					(== normalEgoView vLgrassSkirt)
					(== egoState WEARING_GRASS_SKIRT)
					(or	(== ((inventory at iWood) view) ivSculpture)
							(== ((inventory at iWood) view) ivSculptureModest)))
			(= beachState SELLING_SCULPTURE)
		)

		(if (== egoState DOING_IT)
			(= beachState SCREWING)
			(= curVendor vVendorKnife)
		)

		(if (< prevRoomNum curRoomNum)	;**	only if from 250!
			(ego
				posn				1 174,
				loop				facingEast,
			)
		)

		(NormalEgo)

		(switch beachState
			(T_NOT_YET_HERE
				(Load VIEW vLdrowning)
				(aLizard init)
			)

			(T_ON_TOWEL
				(ego
					observeControl		cYellow,
				)
			)

			(LOOKING_AT_TAWNI
				(aTawni
					setLoop				lStandingUp,
					setCel				LastCel,
				)
				(TawniScript changeState takeOffTop)
				(ego
					observeControl		cYellow,
				)
				(-- beachState)				;**	back to "T_ON_TOWEL"
			)

			(SCREWING
				(Load VIEW (- vL&TdoingIt (* 5 (>= filthLevel DIRTY))))
				(Load VIEW vVendorKnife)
				(Load SOUND sForeplay)
				(Load SOUND sInterrupted)
				(Load SOUND sSurf)
				(Load SOUND sKeystone)
				(aTawni
					illegalBits		0,
					ignoreActors,
					view				(- vL&TdoingIt (* 5 (>= filthLevel DIRTY))),
					loop				elTturningLeft,
					cel				0,
				)
				(ego
					illegalBits    0,
					ignoreActors,
					view				normalEgoView,
					loop				facingEast,
					posn				132 TawniY,
					setMotion		MoveTo 138 TawniY,
				)
				(RoomScript changeState getLaid)
			)

			(T_PISSED
				(aTawni	 			  	init)
				(ego
					observeControl		cYellow,
				)
			)

			(SELLING_SCULPTURE
				(Load VIEW vLgrassSkirt)
				(Load VIEW ivSculpture)
				(Load VIEW ivSculptureModest)
				(HandsOff)
				(SetFlg noCursor)
				(= curVendor vLgrassSkirt)
				(= egoState WEARING_GRASS_SKIRT)
				(aTawni					init)
			)

			(TOWEL_ON_BEACH
			 	(= curVendor NONE)
			  	(aTowel					init)
			)

			(BEACH_DONE
			  	(aTowel
					init,
					hide,
				)
			 	(= curVendor NONE)
			)
		)


		(if (and (not egoIsPatti)
					(>= beachState TOWEL_ON_BEACH))
			(Load VIEW (+ vLundressing egoIsHunk))
			(Load VIEW vLwithTowel)
			(Load VIEW vLstooping)
			(aLizard init)
		)

		(if (!= beachState SELLING_SCULPTURE)
			(ego			init)
		)

		(if (and beachState
					(< beachState TOWEL_ON_BEACH))
			(aTowel				init)
			(aTawni				init)
			(if (== beachState SCREWING)
				(TawniScript changeState TinLimbo)
			)
		)

		(if (and curVendor
					beachState
					(not demo)
			 		(!= curVendor NONE)
			 		(< beachState TOWEL_ON_BEACH))
			(aVendor init)
		)

		(if (and	(not demo)
					(!= beachState SCREWING))
			(music
				number			sSurf,
				loop				FOREVER,
				play,
			)
		)

		(if (and	(== egoState GOING_TO_BIG_FACE)
					(or	(== prevRoomNum 266)
							(== prevRoomNum 265)))
			(= egoState NORMAL)
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if (& (ego onControl) cLtBlack)
			(if (not seenMsg)
				(= seenMsg TRUE)
				(Printf "You have no desire to swim in a lagoon as polluted
					as this, %s!" egoName)
			)
		else
			(= seenMsg FALSE)
		)

		(if (and (!= egoState NORMAL)				;**	Note:  ANY pixel on water
					(!= egoState DROWNING))
			(ego observeControls cLtBlue)
		)

		(if (and (== (ego onControl) cLtBlue)	;**	Note:  EVERY pixel on water
					(== egoState NORMAL))
			(self changeState drowning)
		)
	)


	(method (handleEvent event)
		(if (and (not (super handleEvent event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'cut, carve')
				(Print "Not here; someone might see you!")
			)

			((Said 'get/crab')
				(Print "You are perfectly content to allow the sand crabs 
					to continue running everywhere in this area.
					Just be happy they aren't crawling on you!")
			)

			((or	(Said '(get<on), spread, use, lie, lay/towel')
					(Said '(bathe<sun), sunbathe')
					(Said 'get/tan, suntan')
					(Said '/(bathe<sun), sunbathe')
					(Said 'remove/clothes')
			 		(Said 'lie, lay/down[<on]/towel'))
				(cond
					((== egoState LYING_ON_TOWEL)
						(YouAre)
					)
					((== egoState WEARING_GRASS_SKIRT)
						(Print "Change clothes first.")
					)
					((!= egoState NORMAL)
						(NotNow)
					)
					((== beachState T_ON_TOWEL)
						(Print "That girl seems to be using almost all of her 
							towel!")
					)
					((== beachState T_PISSED)
						(Print "\"Share my towel?\"
							asks Tawni. \"Try it, and I'll break your...\"")
						(Print "...arm!")
					)
					((== beachState TOWEL_ON_BEACH)
						(Ok)
						(= lieDownAfterTakingTowel TRUE)
						(self changeState gotoTtowel)
					)
					((not (ego has iTowel))
						(DontHave)
					)
					(else
						(Ok)
						(self changeState gotoLtowel)
					)
				)
			)

			((or	(Said 'rise, (stand<up), (get<off), (get<up)')
					(Said 'sunbathe<stop')
			 		(Said 'leave/towel'))
				(cond
					((!= egoState LYING_ON_TOWEL)
						(Print "How can you do that?")
					)
					(else
						(self changeState getUp)
					)
				)
			)

			((Said '/towel>')
				(cond
					((ego has iTowel)
						(event claimed FALSE)	;**	send it to room 0
					)
					((or	(& (aTowel signal) actorHidden)
							(not (cast contains aTowel)))
						(Print "There is no towel here.")
						(Print "But... you can order an official
							\"Leisure Suit Larry 2\" beach towel just by dialing 
							1 (800) 326-6654 (or 1 (209) 683-4468) today!!")
						(event claimed TRUE)
					)
					((Said 'get, grab, steal, (pick<up)')
						(cond
							((== egoState WEARING_GRASS_SKIRT)
								(Print "Change clothes first.")
							)
							((!= egoState NORMAL)
								(NotNow)
							)
							((== beachState T_ON_TOWEL)
								(Print "You can't take the towel when that girl
									is lying upon it.")
							)
							((== beachState T_PISSED)
								(Print "\"Take my towel from me?\"
									asks Tawni. \"Try it, and I'll break your...\"")
								(Print "...arm!")
							)
							((!= beachState TOWEL_ON_BEACH)
								(Print "How can you do that?")
							)
							(else
								(Ok)
								(self changeState gotoTtowel)
							)
						)
					)
					((Said 'look')
						(cond
							((and (>= beachState T_ON_TOWEL)	;**	Towel's here.
									(<= beachState SELLING_SCULPTURE))
								(Print "Wouldn't you love to be in its place?")
							)
							((< beachState TOWEL_ON_BEACH)
								(Print "There's a towel under that girl.")
							)
							((== beachState TOWEL_ON_BEACH)
								(Print "Oh, look! Tawni forgot her towel.")
							)
							((== egoState LYING_ON_TOWEL)
								(Print "The towel is under you,
									protecting you from the sand crabs!")
							)
							(else
								(event claimed FALSE)	;**	send it to room 0
							)
						)
					)
					(else
						(event claimed FALSE)	;**	send it to room 0
					)
				)
			)


			((Said 'look>')
				(cond
					((Said '/rock, stone')
						(Print "Occasionally lizards love to scamper across
							the rocks here at Sunaffa Beach.")
					)

					((Said '/ocean, water, wave')
						(Print "The reflection of the brilliant tropical sun
							off the waters of Nontoonyt Lagoon has a beautiful,
							aquamarine glow, due in large part to the quantity
							of raw sewage dumped directly into the water by Natives, Inc.,
							although zillions in corporate revenue have been saved
							in the process of non-processing.")
						(Print "You suppose that means swimming is not recommended.")
					)

					((or	(Said 'down<look')
							(Said '/beach, down, sand'))
						(Print "How interesting.
							Hundreds of tiny sand crabs are running about all
							over the sand of beautiful Sunaffa Beach.")
					)

					((Said '/crab')
						(Print "The sand crabs are everywhere.
							You feel fortunate they aren't crawling all over you!")
					)

					((Said '[/room]')
						(if (and beachState
									(< beachState SELLING_SCULPTURE))
							(Print "There is a beautiful topless woman lying here,
								sunbathing on her towel.")
						else
							(Print "Beautiful Sunaffa Beach is deserted.")
						)
						(Print "There is something moving in the sand.")
					)
				)
			)
		)
	)


	(method (changeState newState &tmp rollX jumpX)
		(switch (= state newState)

			(intro
;**					(if demo
;**						(delay 2)
;**					)
			)

;**				(demo1
;**					(Print "Well, well. Look at this.
;**						A lovely young lady lying all alone on the beach!"
;**						BIG #dispose #time 6)
;**					(delay (+ 2 6))
;**				)
;**	
;**				(demo2
;**					(Print "She's probably lonely too;
;**						just waiting for some clever guy like you, Larry!
;**						Why don't we take a closer look..."
;**						BIG #dispose #time 8)
;**					(delay (+ 2 8))
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(curRoom newRoom 266)
;**				)
;**	
;**				(demo4
;**					(cls)
;**					(curRoom newRoom 266)
;**				)


			;**	ego is completely on the water

			(drowning
				(delay 0)
				(= cycles 0)
				(HandsOff)
				(= egoState DROWNING)
				(ego
					view				(if egoIsPatti vPdrowning
										else				vLdrowning),
			 		illegalBits		( & $FFFF ( ~ cLtBlue)),	;**	Stay on water.
					cycleSpeed		1,
					moveSpeed		1,
					setStep			2 2,
					setCycle			Forward,
				)
				(delay 3)
			)

			(deathOccurredSuddenly
				(ego
					setMotion		FALSE,
					setCycle			EndLoop self,
				)
			)

			(deathDelay
				(ego hide)
				(delay 2)
			)

			(UBdying
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		(+ 4 (ego view)),	;**	dead is 4 more than drowning
					register 	(Format @string
										"Next time, %s, 
										stay out of that polluted lagoon!" egoName),
					next			(Format @string2 "You're in over your head again"),
				)
			)


			;**	Larry bends over and gets towel

			(gotoTtowel
				(HandsOff)
				(ego
					setMotion		MoveTo 126 155 self
				)
			)

			(bendOverTtowel
				(ego
					view				vLstooping,
					setLoop			0,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(takeTtowel
				(aTowel				hide)
				(ego
					get 				iTowel,
					setCycle			BegLoop self
				)
				(= beachState BEACH_DONE)
				(theGame changeScore 2)
			)

			(TtowelDone
				(if lieDownAfterTakingTowel 
					(= lieDownAfterTakingTowel FALSE)
					(self cue)
				else
					(NormalEgo)
				)
			)


			;**	Larry puts down towel and lays down

			(gotoLtowel
				(HandsOff)
				(ego
					illegalBits		0,
					setMotion		MoveTo 151 170 self,
				)
				(LizardScript changeState emergencyExit)
			)

			(spreadTtowel
				(ego
					view				vLwithTowel,
					setLoop			lSpreadingTowel,
					cel				0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(getOnTowel
				(aTowel
					view				vLwithTowel,
					loop				lLarryTowel,
					posn				LarryTowelX LarryTowelY,
					show,
					stopUpd,
				)
				(ego
					view				normalEgoView,
					cycleSpeed		0,
					setCycle			Walk,
					setMotion		MoveTo LarryTowelX LarryTowelY self
				)
			)

			(undressLarry
				(ego
					cycleSpeed		1,
					view				(+ vLundressing egoIsHunk),
					setLoop			0,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(lieDown
				(ego
					view				vLwithTowel,
					setLoop			lLlyingDown,
					cel				0,
					setCycle			EndLoop self,
				)
				(LizardScript changeState hereComesLizardDick)
			)

			(onLtowel
				(= egoState LYING_ON_TOWEL)
				(User canInput TRUE)
				(delay 5)
			)

			(startRoasting
				(Print "The tropical sun warms you all over.")
				(delay 5)
			)

			(roasting
				(Print "You know, a guy could get a nice suntan here.")
				(delay 5)
			)

			(cooked
				(if (TstFlg scoredTan)
					(Print "You had a pretty good tan when you laid down here.")
					(delay 4)
				else
					(SetFlg scoredTan)
					(theGame changeScore 30)
					(Print "Your tan now looks MAHvelous.")
					(delay 10)
				)
			)

			(wellDone
				(Print "As the tropical sun slices through a hole in the
					severely depleted ozone layer directly above the pollution
					of Nontoonyt,
					you seem to remember reading an article somewhere
					about UV overexposure being dangerous...")
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vLdeadSunburn,
					register 	(Format @string "Some guys never learn!"),
					next			(Format @string2 "Another case of executive burnout"),
				)
			)


			;**	Larry gets up off towel

			(getUp
				(HandsOff)
				(Ok)
				(delay 0)
				(ego
					view				vLwithTowel,
					setCycle			BegLoop self,
				)
			)

			(getDressed
				(ego
					view				(+ vLundressing egoIsHunk),
					setLoop 		   0,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(gotoTheTowel
				(ego
					cycleSpeed		0,
					view				normalEgoView,
					setLoop			RELEASE,
					setCycle			Walk,
					setMotion		MoveTo 151 169 self,
				)
			)

			(bendOverLtowel
				(ego
					cycleSpeed		1,
					view				vLstooping,
					setLoop			facingEast,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(gotLtowel
				(aTowel				hide)
				(ego
					setCycle			BegLoop self
				)
			)

			(LtowelDone
				(NormalEgo)
				(= egoState NORMAL)
			)


			;**  	Larry gets laid

			(getLaid
				(HandsOff)
				(SetFlg noCursor)
				(delay 0)
				(aTawni
					setScript		FALSE,
					setCycle			EndLoop self,
				)
				(music
					number			sForeplay,
					loop				FOREVER,
					play
				)
			)

			(TjumpsL
				(aTawni				hide)
				(ego
					posn				TawniX TawniY,
					view				(- vL&TdoingIt (* 5 (>= filthLevel DIRTY))),
					loop				elJumpingL,
					cel				0,
					setCycle			EndLoop self,
					cycleSpeed		1,
				)
			)

			(TraisesUp
				(ego
					loop				elTrisingOnL,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(humping
				(ego
					viewer			humpCycler,
					setLoop			lHumping,
					setCycle			Forward,
				)
			)

			;**	wait here until KNIFE VENDOR bumps us to next stage...

			(knifeManReady
				(Print "\"Whacha selling?\" Tawni yells at the peddler.")
				(Print "The peddler smiles. \"I have some fine ginsu knives.\"")
				(Print "\"Excuse me for a moment, will you?\"
					Tawni asks you.
					\"Something else has popped up.\"")
				(music
					number			sInterrupted,
					loop				1,
					play,
				)
				(ego
					viewer			FALSE,
					cycleSpeed		1,
					loop				elDismounting,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(bargain
				(Print "\"Well,\" you think to yourself,
					\"that was rude!\"")
				(delay 3)
			)

			(thatWasRude
				(Print "\"So what do you think, lady?\" says the vendor.
					\"Wouldn't you like one of these fine ginsu knives to
					take home for a souvenir?
					For you, I'd make a special deal!\"")
				(Print "\"Oooohhhh,\" says Tawni, \"is it really a bargain?
					I just LOVE bargains!\"")
				(delay 3)
			)

			(sheffieldSteel
				(Print "\"Most assuredly so,\" says the peddler,
					\"it's made with hand-rubbed hickory handles,
					and a blade of the finest Sheffield carbonized steel,
					drawn from the hottest 100-year old oak charcoal fires,
					honed to perfection by small Oriental virgins,
					and guaranteed for life!\"")
				(Print "(Or until you leave this beach,
					whichever comes first!)" LOW)
				(delay 3)
			)

			(comeInAcase
				(Print "\"I'll take it,\" says Tawni.
					\"Does it come in a carrying case?\"")
				(Print "\"No,\" says the peddler.")
				(if (>= filthLevel DIRTY)
					(Print "\"...but I'll bet you could!\" thinks the peddler.")
				)
				(Print "\"That'll be thirty dollars,\" he concludes.")
				(delay 3)
			)

			(twentyBucksBitch
				(Print "\"Well, let me see now,\" replies Tawni,
					digging into her bikini bottoms.
					\"Oh, gosh, I've only got twenty bucks in cash left.\"")
				(Print "\"But wait, I do have this nice shiny new credit card!\"
					Turning to you, she says,
					\"Hey, hold this knife for me, willya?\"")
				(ego get iKnife)
				(= newspaperState NSshowroom)	;**	Advertise Cherri's show.
				(= showroomState SRshowIsOn)	;**	Showtime!
				(theGame changeScore 40)
				(delay 3)
			)

			(givesMoney
				(Print "\"Thanks ever so much, madam,\" says the native,
					sliding his imprinter over your former credit card.
					\"It's a pleasure doing business with a real pro!\"")
				(Print "Turning back to you, Tawni says,
					\"I'm sorry to `interruptus,' my little shopper-whopper,
					but you know: I just can't resist a purchase!\"")
				(Print "\"And now, where were we?\"")
				(VendorScript changeState makeVendorLeave)
				(delay 3)
			)

			(TgoesDownAgain
				(ego
					setCycle			BegLoop self,
				)
			)

			(youContemplate
				(Print "You pause to contemplate her rude behavior.
					Were you too offended by her thoughtless interruption
					to continue making love to the beautiful young Tawni?")
				(delay 3)
			)

			(screwSomeMore
				(Print "Nope.")
				(music
					number			sForeplay,
					loop				FOREVER,
					play
				)
				(ego
					viewer			humpCycler,
					setLoop			lHumping,
					setCycle			Forward,
				)
				(delay 12)
			)

			(larryGetsCrabs
				(music 				fade)
				(Print "Suddenly, you become aware of the hundreds of tiny
					sand crabs that have been crawling inside your leisure suit
					pants since you first laid down on the sand...")
				(music
					number			sKeystone,
					loop				FOREVER,
					play ,			
				)
				(Print "\"YEEEOOOOWWWW!!!\" you scream.
					\"Quick! Get off of me!!\"" HIGH)
				(ego
					viewer			FALSE,
					cycleSpeed		0,
					loop				elDismounting,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(larryRises
				(aTawni
					view				vTawni,
					loop				lStandingUp,
					setCel			LastCel,
					show
				)
				(ego
					loop 		   	elLrising,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(theyAreUp
				(Print "\"Is my lovemaking that good for you,
					my little middle-aged mall-man?\" says Tawni." HIGH)
				(ego
					posn				142 TawniY,
					setLoop			lLscratching,
					setCycle			Forward,
					setStep			1 1,
					setMotion		MoveTo 127 TawniY,
				)
				(= cycles 44)
			)

			(terrible
				(Print (Format @string "\"%s\" you cry.
					\"Tawni, I've got a terrible case of crabs!!\""
					expletive) HIGH)
				(ego
					cycleSpeed		1,
					loop				elLintoPants,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(CRABS
				(Print "\"CRABS?!\" shouts Tawni,
					\"I should have known better than to have anything to
					do with a local.
					Especially, an OLDER local!
					And, a PUDGY older local at that!!\"" HIGH)
				(ego
					viewer			humpCycler,
					loop				lCrabShaking,
					setCycle			Forward,
				)
				(delay 3)
			)

			(somethingInHere
				(Print "As you pull a large crab from your pants, Tawni says,
					\"Like, man!
					I refuse to party with any guy so socially irresponsible!
					Get lost, `Flaccido Domingo!'\"" HIGH)
				(= cycles 11)
			)

			(raiseYourPants
				(aTawni
					view				vTawni,
					setScript		TawniScript,
				)
				(TawniScript changeState TliesDown)
				(ego
					viewer			FALSE,
					loop				elPullingUpPants,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(RweThru
				(Print "\"Tawni, baby!\" you cry,
					\"Does this mean we're through?\"" HIGH)
				(delay 3)
			)

			(roNesponse
				(music
					number			sInterrupted,
					loop				1,
					play				self,
				)
				(Print "There is no response.")
				(NormalEgo facingSouth)
				(ClrFlg noCursor)
				(ego
					observeControl		cYellow,
				)
				(= beachState T_PISSED)
				(= egoState NORMAL)
			)

			(restartTheSurfSound
				(music
					number			sSurf,
					loop				FOREVER,
					play,
				)
			)
		)
	)
)


(instance aTawni of Actor
	(properties
		view					vTawni
		loop					lWiggleAss
		x						TawniX
		y						TawniY
	)

	(method (init)
		(super init)
		(self
			setScript		TawniScript,
			ignoreActors,
		)
	)

	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'give, sell, show>')
				(cond
					((not nearTowel)
						(NotClose)
					)
					((== vLgrassSkirt (ego view))
						(= theObj (inventory saidMe))
						(event claimed FALSE)
						(cond
							((Said '[/noword]')
								(Print "You must be more specific.")
							)
							((not theObj)
								(Print "Better check your inventory, Larry.
									You don't have one of those.")
							)
							((not	(theObj ownedBy ego))
								(DontHave)
							)
							((== theObj (inventory at iWood))
								(Print "\"Hunks of wood for sale!\" you shout.")
								(Print "\"Who'd want a piece of wood for a souvenir?\"
									responds Tawni.
									\"I can pick up one of those anywhere!
									No thanks, little native man.\"")
							)
							((== theObj (inventory at iSoap))
								(Print "\"Souvenir soap!\"")
								(Print "\"Souvenir soap!\"")
								(Print "\"Soap?! You're trying to sell me soap?
									What a lousy souvenir. 
									Get out of here, pudgy nature boy!\"")
							)
							((== theObj (inventory at iOrchids))
								(Print "\"Wildflowers?
									Why would anyone buy flowers from a beach vendor
									when they grow wild everywhere on this island?\"")
							)
							(else
								(Print "\"No thanks,\" says Tawni,
									\"I'm not interested.\"")
							)
						)
						(Print "At least she didn't recognize you 
							in your clever disguise.
							Now if you only had an authentic Nontoonyt souvenir!")
					)
					((== beachState T_PISSED)
						(Print "\"Oh, wow!\"
							exclaims Tawni, without looking up.
							\"SOUVENIRS! Whacha selling?\"")
						(Print "She turns her head and recognizes you.
							\"Oh, it's you, again!
							Leave me alone, you creep!\"")
						(Print "(Obviously she still remembers you, Larry.
							If only you had some sort of disguise...)" LOW)
					)
					(else
						(Print "You might be able to, 
							if you would look at her first!")
					)
				)
				(event claimed TRUE)
			)

			((or	(Said '//tawni>')
					(Said '/tawni>'))
				(cond
					((> beachState T_PISSED)
						(event claimed TRUE)
						(Print "Where?")
					)

					((Said 'fuck')
						(Print "Yeah, yeah, that's the idea; 
							but as usual, the question is: how?!")
					)

					((Said 'talk')
						(cond
							((not nearTowel)
								(NotClose)
							)
							((== vLgrassSkirt (ego view))
								(Print "\"Oh, wow!\"
									exclaims Tawni, without looking up.
									\"SOUVENIRS! Whacha selling?\"")
								(Print "Larry! 
									She doesn't recognize you in your clever disguise.
									Now if you only had something to sell her!")
							)
							((== beachState T_PISSED)
								(Print "\"Oh, wow!\"
									exclaims Tawni, without looking up.
									\"SOUVENIRS! Whacha selling?\"")
								(Print "She turns her head and recognizes you.
									\"Oh, it's you, again!
									Leave me alone, you creep!\"")
								(Print "(Obviously she still remembers you, Larry.
									If only you had some sort of disguise...)" LOW)
							)
							(else
								(Print "Before speaking to a woman,
									it is advisable to look at her, Larry.")
							)
						)
					)

					((Said 'look')
						(cond
							((== vLgrassSkirt (ego view))
								(Print "She doesn't seem to recognize you in 
									your clever disguise.")
							)
							((== beachState T_PISSED)
								(Print "You wonder if she still has that twenty
									dollars she got in change from the 
									souvenir bead vendor.")
							)
							((== tawniBusy TRUE)
								(Print "Now? She seems to be interested in that
									beach vendor.")
							)
							((not nearTowel)
								(NotClose)
							)
							(else
								(if (not (TstFlg saidHiToTawni))
									(Printf "\"Hello,\" you say to the beautiful young
										girl on the towel. %s" laffer)
								)
								(HandsOff)
								(SetFlg noCursor)
								(= beachState LOOKING_AT_TAWNI)
								(TawniScript changeState getTawniUp)
							)
						)
					)
				)
			)
		)
	)
)


(instance TawniScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(Tintro
				(delay (Random 3 7))
			)

			(wiggleAss
				(aTawni
					cycleSpeed	1,
					setLoop		lWiggleAss,
					setCycle		EndLoop,
				)
				(delay (Random 1 3))
			)

			(wiggleAss2
				(aTawni
					setCycle		BegLoop ,
				)
				(delay (Random 3 5))
			)

			(turnHead
				(aTawni
					setLoop		(Random lTurningHeadR lTurningHeadL),
					setCycle		EndLoop,
				)
				(delay (Random 3 5))
			)

			(turnHead2
				(aTawni
					setCycle		BegLoop self,
				)
				(= state (- Tintro 1))
			)

			;**	stand Tawni up and maybe raise her top and maybe change rooms

			(getTawniUp
				(delay 0)
				(aTawni
					cycleSpeed	2,
					setLoop		lStandingUp,
					cel			0,
					setCycle		EndLoop self,
				)
				(if (>= filthLevel DIRTY)
					(++ state)
				)
			)

			(pullUpTop
				(aTawni
					setLoop		lTyingTop,
					cel			0,
					setCycle		EndLoop self,
				)
			)

			;**	park at this stage until cue'd by some other script...

			(goto266
				(if (== beachState LOOKING_AT_TAWNI)
					(if (not (TstFlg saidHiToTawni))
						(SetFlg saidHiToTawni)
						(Print "\"Hello, Larry,\" replies the blond.
							\"My name is Tawni.\"")
					)
					(= egoState GOING_TO_BIG_FACE)
					(curRoom newRoom 266)
				)
			)

			(takeOffTop
				(delay 0)
				(if (>= filthLevel DIRTY)
					(self cue)
				else
					(aTawni
						setCycle 	BegLoop self
					)
				)
			)

			(TliesDown
				(aTawni
					setLoop		lStandingUp,
					setCel		LastCel,
					setCycle 	BegLoop self,
				)
				(= tawniBusy FALSE)
				(= state (- wiggleAss 1))
			)


			(TinLimbo
				(delay 0)
			)
		)
	)
)


(instance aVendor of Actor
	(method (init)
		(super init)
		(self
			view				curVendor,
			setCycle			Walk,
			setScript		VendorScript,
		)
	)
)

(instance VendorScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'look/vendor, man')
				(if (or	( < state bringVendorIn)
							( > state makeVendorLeave))
					(Print "Where?")
				else
					(Print "Don't you just love these colorful native types?")
				)
			)

			((Said 'talk/vendor, man')
				(if (or	( < state bringVendorIn)
							( > state makeVendorLeave))
					(Print "Where?")
				else
					(Print "\"Uh, excuse me, sir,\" you ask.
						\"Do you have a permit to do this?\"")
					(Print "\"Beat it, bub!\" he whispers under his breath,
						carefully preventing the woman from hearing him.
						\"Can't you see I'm just trying to make a quick buck
						off the Americano here?\"")
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(vendorInit
				(aVendor
					posn		-15 123,			;**	(Random 114 163)
				)
				(switch curVendor
					(vLgrassSkirt
						(self cue)
					)
			 		(vVendorKnife
						(if (<= filthLevel CLEAN)
							(= cycles 99)
						else
							(= cycles 222)
						)
					)
					(else
						(delay (Random 5 11))
					)
				)
			)

			(bringVendorIn
				(= tawniBusy TRUE)
				(aVendor
					setLoop			facingEast,
					view				curVendor,
					setMotion		MoveTo 137 149 self
				)
				(= cycles 11)
			)

			(souvenirs
				(Print "\"Souvenirs!\"")
				(= cycles 11)
			)

			(souvenirs2
				(Print "\"Souvenirs!\"")
				(= cycles 11)
			)

			(getYour
				(Print "\"Get your genuine Nontoonyt souvenirs!\"")
			)

			(wannaBuyA
				(if (and (!= beachState SCREWING)
							(>= (TawniScript state) getTawniUp))
					(return)
				)
				(Print "\"Oh, wow!\" exclaims Tawni, \"SOUVENIRS!\"")
				(aVendor
					setLoop 		(- (NumLoops aVendor) 1),
				)
				(if (== curVendor vVendorKnife)
					(aVendor 
						setCycle 	Forward,
					)
					(RoomScript changeState knifeManReady)
					(return)					;**	so timer never starts
				)
				(aVendor
					viewer		salesViewer,
				)
				(Print "\"Whacha selling?\" she asks the peddler.")
				(switch curVendor
					(vVendorCarvings
						(Print "\"Genuine plastic souvenirs, handmade
							in the city of rsubfqsdf.\"")
					)
					(vVendorShells
						(Print "\"Special souvenir seashells,
							mounted on a free-swinging necklace made of
							real Nontoonyt string.\"")
			 		)
					(vVendorBeads
						(Print "\"Today, only, I have a very special deal
							on these wonderful, souvenir beads.\"")
					)
					(vLgrassSkirt
						(Print "\"Genuine native-islander, designer-labeled
							erotic carvings for only twenty dollars!\" you cry.
							\"But you'd better hurry; I only have ONE left!\"")
					)
				)
				(TawniScript changeState getTawniUp)
				(= cycles 30)
			)

			(buyThisOne
				(switch curVendor
					(vVendorCarvings
						(Print "\"What was that?\" asks the girl.")
					)
					(vVendorShells
						(Print "\"Seashells?\" she surmises,
							\"I see you sell seashells at this slimy seashore!\"")
						(Print "\"Exactly how are your shells different from the
							ones lying all over Sunaffa Beach?\"")
			 		)
					(vVendorBeads
						(Print "\"Ooooh, I just love jewelry,\" says the girl.
							\"Would you allow me to purchase some?\"")
					)
					(vLgrassSkirt
						(Print "\"Well,\" says Tawni, \"I might be interested.
							Does it have any special features?\"")
						(Print "(Your disguise seems to be working, Larry;
							Tawni doesn't recognize you!)" LOW)
					)
				)
				(= cycles 30)
			)

			(maybeBuyIt
				(switch curVendor
					(vVendorCarvings
						(Print "\"(Hong Kong.)\"
							the vendor mumbles under his breath.")
					)
					(vVendorShells
						(Print "\"These are no longer contaminated with
							that crude oil/sewage mixture we call ocean water.\"")
			 		)
					(vVendorBeads
						(Print "\"Sure, if you're nice to me.
							And, give me lots of American money!\"")
					)
					(vLgrassSkirt
						(if (== ((Inventory at iWood) view) ivSculpture)
							(Print "\"I'm glad you asked,\" you respond,
								\"there is one small part that might attract your eye!\""
								#icon			ivSculpture 0 0
							)
						else
							(Print "\"No, but it is hand-made!\""
								#icon			ivSculptureModest 0 0
							)
						)
					)
				)
				(= cycles 30)
			)

			(wannaBuyThis
				(switch curVendor
					(vVendorCarvings
						(Print "\"Oh, no matter. As long as they're genuine,
							give me several.
							You never know when I might get back this way and
							I just love buying souvenirs!\"")
					)
					(vVendorShells
						(Print "\"Oh. Well, better let me have a few,\"
							she says, \"here's fifty bucks.  Can I get lots?\"")
			 		)
					(vVendorBeads
						(Print "\"Ok. Here. Take all I've got,\" she says.")
						(Print "(Boy, this gal will buy anything!)" LOW)
					)
					(vLgrassSkirt
						(Printf "\"%s It's a deal,\" she says,
							\"I don't know much about art...
							but I know what I like!\"" expletive)
					)
				)
				(= cycles 30)
			)

			(itsAsale
				(aVendor
					loop			facingEast,
					setCycle		Walk,
					viewer		FALSE,
				)
				(switch curVendor
					(vVendorCarvings
						(Print "\"All together, that comes to...\"
							the peddler pauses,
							considering what the market will bear,
							\"...three hundred dollars, American.
							And, I'll cover the taxes for you!\"")
						(Print "\"Really? That expensive?
							Well, ok, if you say so.\"")
						(Print "\"Hey, thanks a lot,\" says the vendor.
							\"Have a nice day!\"")
					)
					(vVendorShells
						(Print "\"Sure, here's a bag full,\" says the vendor.
							\"Have a nice day!\"")
			 		)
					(vVendorBeads
						(Print "\"Hey, thanks a lot,\" says the vendor,
							\"But why don't you keep this twenty?
							You might need it.\"")
						(Print "\"Have a nice day!\"")
					)
					(vLgrassSkirt
						(Print "\"You're lucky.
							I'm down to my last twenty bucks!\" says Tawni.
							\"Here you are.\"")
						(theGame changeScore 35)
						(ego
							get			iMoney,
							put 			iWood LIMBO,
						)
						(= dollars 20)
						(Print "\"Thank you very much, Miss.
							And... use it in good health.\"")
					)
				)
				(TawniScript cue)
				(= cycles 30)
			)

			(makeVendorLeave
				(aVendor
					cycleSpeed		0,
					setLoop			facingWest,
					setCycle			Walk,
					setMotion		MoveTo -30 (Random 114 163) self
				)
			)

			(vendorGone
				(cond
					((== curVendor vLgrassSkirt)
						(= curVendor NONE)
						(= beachState TOWEL_ON_BEACH)
						(curRoom newRoom 250)
					)
					((or	(== curVendor vVendorBeads)
							(== curVendor vVendorKnife))
						(= curVendor NONE)
					)
					(else
						(++ curVendor)
						(self changeState vendorInit)
					)
				)
			)
		)
	)
)

(instance aTowel of View
	(properties
		view					vTawni
		loop					lTawniTowel
		x						TawniTowelX
		y						TawniTowelY
	)
	(method (init)
		(super init)
		(self
			setPri				4,
			ignoreActors,
			stopUpd,
		)
	)
)

(instance aLizard of Actor
	(properties
		view					vLizard
		illegalBits			0
		priority				15
	)

	(method (init)
		(super init)
		(self
			setCycle			Walk,
			setScript		LizardScript,
			setPri			15,
			ignoreActors,
		)
	)
)

(instance LizardScript of Script
	(method (doit)
		(super doit)

		(if (and (== state LizLicking)
					(>= (RoomScript state) getUp)
					(<= (RoomScript state) LtowelDone))
			(self cue)
		)
	)

	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(if (and state
					(Said '/lizard>'))
			(cond
				((Said 'get, grab/')
					(Print "He's too fast for you; you'd never catch him.")
				)

				((Said 'look/')
					(cond
						((== state LizIntro)
							(Print "Where?")
						)
						((and (== state LizLicking)
								(== egoState LYING_ON_TOWEL))
							(Print "What a nasty looking little devil he is!")
						)
						(else
							(Print "A bright red lizard scampers across the rocks
								near Sunaffa Beach.")
						)
					)
				)

				(else
					(Print "Leave the lizard alone.
						He's of no use to you.")
					(event claimed TRUE)
				)
			)
		)
	)

	(method (changeState newState)
		(switch (= state newState)

			(LizIntro
				(if (Random 0 1)
					(aLizard
						setLoop			facingEast,
						posn				-20 (Random 172 188),
					)
				else
					(aLizard
						setLoop			facingWest,
						posn				218 198,
					)
				)
				(delay (Random 10 20))
			)

			(bringLizIn
				(aLizard
					setMotion		MoveTo (Random 126 192) (Random 172 188) self
				)
			)

			(pauseB4standing
				(delay (Random 2 5))
			)

			(standLizUp
				(aLizard
					cycleSpeed		1,
					setLoop			(+ 2 (aLizard loop)),
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(LizStanding
				(= cycles (Random 3 7))
			)

			(LizLicking
				(aLizard
					setLoop			(+ 2 (aLizard loop)),
					cel				0,
					setCycle			Forward,
				)
				(if (and	(<= (RoomScript state) wellDone)
							(>= (RoomScript state) onLtowel))
					(delay 7)
				 else
				 	(= cycles (Random 5 9))
				)
			)

			(makeLizGoDown
				(aLizard
					setLoop			(- (aLizard loop) 2),
					setCel			LastCel,
					setCycle			BegLoop	self,
				)
			)

			(byeByeLiz
				(aLizard
					setLoop			(- (aLizard loop) 2),
					cycleSpeed		0,
					setCycle			Walk,
				)
				(if (== facingRight (aLizard loop))
					(aLizard
						setMotion		MoveTo 218 198	self
					)
				else
					(aLizard
						setMotion		MoveTo -55 (Random 172 188) self
					)
				)
				(= state readyToStartOver)
			)


			;**	Larry's spreading his towel; get your lizard outta here!

			(emergencyExit
				(= cycles 0)
				(delay 0)
				(aLizard
					setCycle			Walk,
					setLoop			facingWest,
					setMotion		MoveTo (- (aLizard x) 33) 222 ,
				)
			)

			(hereComesLizardDick
				(if (>= filthLevel DIRTY)
					(aLizard
						setLoop			facingEast,
						posn				-20 180,
						setCycle			Walk,
						setMotion		MoveTo 148 188 self
					)
				)
			)

			(LizHalfOut
				(aLizard
					setMotion		MoveTo 192 167 self
				)
			)

			(lickDick
				(aLizard
					cycleSpeed		1,
					setLoop			lLizRisingEast,
					cel				0,
					setCycle			EndLoop self,
				)
				(=  state (- LizStanding 1))
			)
		)
	)
)

(instance humpCycler of Code
	(method (doit)
		(cond
			((<= filthLevel CLEAN)
				(ego setCycle FALSE)
			)
			((not (Random 0 9))
				(ego cycleSpeed (Random 0 5))
			)
		)
	)
)

(instance salesViewer of Code
	(method (doit)
		(if (not (Random 0 3))
			(aVendor
				setCel 		(Random 0 (- (NumCels aVendor) 1))
			)
		)
	)
)

